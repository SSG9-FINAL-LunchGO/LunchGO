package com.example.LunchGo.member.service;

import com.example.LunchGo.member.repository.OwnerRepository;
import com.example.LunchGo.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerScheduler {

    private final OwnerRepository ownerRepository;
    private final SmsService smsService;

    /**
     * 매일 오전 12시 3분마다 식당 미등록 사업자에게 안내 문자 발송
     * 대상: 가입 후 4일 ~ 6일이 지난 활성 상태(ACTIVE)의 사업자 중 식당을 등록하지 않은 경우
     */
    @Scheduled(cron = "0 3 0 * * *") // 매일 오전 12시 3분 실행
    public void sendRestaurantRegistrationReminder() {
        log.info("[OwnerScheduler] Start sending restaurant registration reminders.");

        // 가입 후 4일 ~ 6일이 지난 대상 조회 (예: 6일 전 <= 가입일 <= 4일 전)
        LocalDateTime endDateTime = LocalDateTime.now().minusDays(4);
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(6);

        List<String> ownerPhones = ownerRepository.findPhonesByActiveAndNoRestaurant(startDateTime, endDateTime);

        if (ownerPhones.isEmpty()) {
            log.info("[OwnerScheduler] No owners found for registration reminder.");
            return;
        }

        log.info("[OwnerScheduler] Found {} owners. Sending SMS...", ownerPhones.size());

        // 배포 환경에서 동작하기 때문에, 일단 주석 처리하여 문자가 주기적으로 자동 발송되는 것을 방지
        try {
//            smsService.sendRestaurantRegistrationNotice(ownerPhones);
            log.info("[OwnerScheduler] SMS sending completed.");
        } catch (Exception e) {
            log.error("[OwnerScheduler] Failed to send SMS.", e);
        }
    }
}
