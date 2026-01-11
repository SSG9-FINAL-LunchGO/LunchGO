package com.example.LunchGo.restaurant.service;

import com.example.LunchGo.ai.service.AiChatService;
import com.example.LunchGo.reservation.dto.BusinessReservationItemResponse;
import com.example.LunchGo.reservation.service.BusinessReservationQueryService;
import com.example.LunchGo.restaurant.entity.DailyRestaurantStats;
import com.example.LunchGo.restaurant.repository.DailyRestaurantStatsRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantStatsService {
    private final BusinessReservationQueryService businessReservationQueryService;
    private final DailyRestaurantStatsRepository dailyRestaurantStatsRepository;
    private final AiChatService aiChatService;
    @Value("${pdf.korean-font-path:}")
    private String koreanFontPath;

    public byte[] generateWeeklyStatsPdf(Long restaurantId) {
        List<BusinessReservationItemResponse> allReservations =
            businessReservationQueryService.getList(restaurantId);

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);

        List<BusinessReservationItemResponse> weeklyReservations = allReservations.stream()
            .filter(item -> isWithinLast7Days(item.getDatetime(), start, end))
            .toList();

        List<DailyRestaurantStats> dailyStats =
            dailyRestaurantStatsRepository.findLast7DaysByRestaurantId(restaurantId);

        String prompt = buildPrompt(restaurantId, start, end, weeklyReservations, dailyStats);
        String aiSummary = aiChatService.chat(prompt);

        return buildPdf(restaurantId, start, end, weeklyReservations, dailyStats, aiSummary);
    }

    private boolean isWithinLast7Days(String datetime, LocalDate start, LocalDate end) {
        if (datetime == null || datetime.isBlank()) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US);
        LocalDate date = LocalDateTime.parse(datetime, formatter).toLocalDate();
        return !date.isBefore(start) && !date.isAfter(end);
    }

    private String buildPrompt(
        Long restaurantId,
        LocalDate start,
        LocalDate end,
        List<BusinessReservationItemResponse> reservations,
        List<DailyRestaurantStats> dailyStats
    ) {
        Map<LocalDate, Integer> reservationCounts = new TreeMap<>();
        Map<LocalDate, Integer> reservationAmounts = new TreeMap<>();

        for (BusinessReservationItemResponse item : reservations) {
            LocalDate date = LocalDate.parse(item.getDatetime().substring(0, 10));
            reservationCounts.merge(date, 1, Integer::sum);
            int amount = item.getAmount() == null ? 0 : item.getAmount();
            reservationAmounts.merge(date, amount, Integer::sum);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("다음 데이터를 기반으로 주간 통계 요약을 한국어로 리포트 형식으로 1-2페이지로 작성해주세요.\n");
        builder.append("형식: 1) 일주일 간 예약 요약을 바탕으로 예약 추이 및 상세 분석 2) 일자별 식당 통계 추이 및 상세 분석 3) 1,2번을 통합하여 해당 사업자에게 추천 또는 조언 및 결론\n");
        builder.append("레스토랑 ID: ").append(restaurantId).append("\n");
        builder.append("기간: ").append(start).append(" ~ ").append(end).append("\n\n");

        builder.append("일주일 동안의 일자별 예약 요약 (건수, 합계금액):\n");
        for (Map.Entry<LocalDate, Integer> entry : reservationCounts.entrySet()) {
            int amount = reservationAmounts.getOrDefault(entry.getKey(), 0);
            builder.append(entry.getKey()).append(" => ")
                .append(entry.getValue()).append(", ")
                .append(amount).append("\n");
        }

        builder.append("\n일자별 식당 통계:\n");
        dailyStats.stream()
            .sorted(Comparator.comparing(stats -> stats.getId().getStatDate()))
            .forEach(stats -> builder.append(stats.getId().getStatDate()).append(" | ")
                .append("view=").append(nullToZero(stats.getViewCount())).append(", ")
                .append("try=").append(nullToZero(stats.getTryCount())).append(", ")
                .append("confirm=").append(nullToZero(stats.getConfirmCount())).append(", ")
                .append("visit=").append(nullToZero(stats.getVisitCount())).append(", ")
                .append("noshow=").append(nullToZero(stats.getDefendedNoshowCnt())).append(", ")
                .append("penalty=").append(nullToZero(stats.getPenaltyStlAmt())).append(", ")
                .append("revenue=").append(nullToZeroLong(stats.getRevenueTotal()))
                .append("\n"));

        builder.append("\n응답은 반드시 한국어로 작성하세요.");
        return builder.toString();
    }

    private byte[] buildPdf(
        Long restaurantId,
        LocalDate start,
        LocalDate end,
        List<BusinessReservationItemResponse> reservations,
        List<DailyRestaurantStats> dailyStats,
        String aiSummary
    ) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                PDType0Font font = loadKoreanFont(document);
                content.beginText();
                content.setFont(font, 16);
                content.newLineAtOffset(50, 750);
                content.showText("주간 식당 통계 리포트");
                content.newLineAtOffset(0, -20);

                content.setFont(font, 12);
                content.showText("식당 ID: " + restaurantId);
                content.newLineAtOffset(0, -16);
                content.showText("기간: " + start + " ~ " + end);
                content.newLineAtOffset(0, -20);

                content.showText("예약 건수: " + reservations.size());
                content.newLineAtOffset(0, -16);
                content.showText("일자별 통계 행 수: " + dailyStats.size());
                content.newLineAtOffset(0, -20);

                content.setFont(font, 12);
                content.showText("AI 요약");
                content.newLineAtOffset(0, -16);

                content.setFont(font, 11);
                for (String line : wrapText(aiSummary, 90)) {
                    content.showText(line);
                    content.newLineAtOffset(0, -14);
                }

                content.endText();
            }

            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to generate PDF", e);
        }
    }

    private List<String> wrapText(String text, int maxLineLength) {
        if (text == null || text.isBlank()) {
            return List.of("");
        }
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        java.util.ArrayList<String> lines = new java.util.ArrayList<>();
        for (String word : words) {
            if (line.length() + word.length() + 1 > maxLineLength) {
                lines.add(line.toString());
                line.setLength(0);
            }
            if (line.length() > 0) {
                line.append(' ');
            }
            line.append(word);
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }
        return lines;
    }

    private PDType0Font loadKoreanFont(PDDocument document) throws IOException {
        if (koreanFontPath != null && !koreanFontPath.isBlank()) {
            Path path = Path.of(koreanFontPath);
            if (Files.exists(path)) {
                return PDType0Font.load(document, path.toFile());
            }
        }

        Path windowsFont = Path.of("C:\\Windows\\Fonts\\malgun.ttf");
        if (Files.exists(windowsFont)) {
            return PDType0Font.load(document, windowsFont.toFile());
        }

        try (InputStream stream = RestaurantStatsService.class.getResourceAsStream(
            "/fonts/NotoSansKR-Regular.ttf"
        )) {
            if (stream != null) {
                return PDType0Font.load(document, stream);
            }
        }

        throw new IllegalStateException(
            "Korean font not found. Set pdf.korean-font-path or add /fonts/NotoSansKR-Regular.ttf."
        );
    }

    private int nullToZero(Integer value) {
        return value == null ? 0 : value;
    }

    private long nullToZeroLong(Long value) {
        return value == null ? 0L : value;
    }
}
