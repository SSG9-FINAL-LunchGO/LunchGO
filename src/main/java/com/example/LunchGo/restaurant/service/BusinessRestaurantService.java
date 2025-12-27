package com.example.LunchGo.restaurant.service;

import com.example.LunchGo.restaurant.domain.RestaurantStatus;
import com.example.LunchGo.restaurant.dto.ImageDto;
import com.example.LunchGo.restaurant.dto.RegularHolidayDto;
import com.example.LunchGo.restaurant.dto.RestaurantDetailResponse;
import com.example.LunchGo.restaurant.dto.RestaurantCreateRequest; // Import added
import com.example.LunchGo.restaurant.dto.RestaurantTagDto;
import com.example.LunchGo.restaurant.entity.RegularHoliday;
import com.example.LunchGo.restaurant.entity.Restaurant;
import com.example.LunchGo.restaurant.repository.RegularHolidayRepository;
import com.example.LunchGo.restaurant.repository.RestaurantRepository;
import com.example.LunchGo.tag.entity.SearchTag;
import com.example.LunchGo.tag.repository.SearchTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime; // Import added
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RegularHolidayRepository regularHolidayRepository;
    // private final RestaurantImageRepository restaurantImageRepository; // Removed
    private final SearchTagRepository searchTagRepository; // Needed for restaurant tags

    /**
     * 특정 식당의 상세 정보를 조회합니다. (메뉴 정보 제외)
     *
     * @param restaurantId 식당 ID
     * @return 식당 상세 정보를 담은 RestaurantDetailResponse DTO
     * @throws NoSuchElementException 식당을 찾을 수 없는 경우
     */
    public RestaurantDetailResponse getRestaurantDetail(Long restaurantId) {
        // 1. Restaurant 기본 정보 및 이미지 조회 (Fetch Join 활용)
        Restaurant restaurant = restaurantRepository.findByIdWithImages(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Restaurant not found with id: " + restaurantId));

        // 2. RegularHoliday 조회
        List<RegularHoliday> regularHolidays = regularHolidayRepository.findAllByRestaurantId(restaurantId);

        // 3. Restaurant에 매핑된 태그 ID 조회 및 태그 정보 페치
        List<Long> restaurantTagIds = restaurantRepository.findTagIdsByRestaurantId(restaurantId);
        Map<Long, SearchTag> allRestaurantTagsMap = searchTagRepository.findAllById(restaurantTagIds)
                .stream()
                .collect(Collectors.toMap(SearchTag::getTagId, tag -> tag));

        // 4. DTO 매핑
        List<ImageDto> imageDtos = restaurant.getRestaurantImages().stream()
                .map(img -> ImageDto.builder().imageUrl(img.getImageUrl()).build())
                .collect(Collectors.toList());

        List<RestaurantTagDto> restaurantTagDtos = restaurantTagIds.stream()
                .map(tagId -> {
                    SearchTag tag = allRestaurantTagsMap.get(tagId);
                    return tag != null ? RestaurantTagDto.builder()
                            .tagId(tag.getTagId())
                            .content(tag.getContent())
                            .category(tag.getCategory())
                            .build() : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<RegularHolidayDto> regularHolidayDtos = regularHolidays.stream()
                .map(holiday -> RegularHolidayDto.builder().dayOfWeek(holiday.getDayOfWeek()).build())
                .collect(Collectors.toList());


        return RestaurantDetailResponse.builder()
                .restaurantId(restaurant.getRestaurantId())
                .name(restaurant.getName())
                .phone(restaurant.getPhone())
                .roadAddress(restaurant.getRoadAddress())
                .detailAddress(restaurant.getDetailAddress())
                .description(restaurant.getDescription())
                .status(restaurant.getStatus())
                .avgMainPrice(restaurant.getAvgMainPrice())
                .reservationLimit(restaurant.getReservationLimit())
                .holidayAvailable(restaurant.isHolidayAvailable())
                .preorderAvailable(restaurant.isPreorderAvailable())
                .openTime(restaurant.getOpenTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .closeTime(restaurant.getCloseTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .openDate(restaurant.getOpenDate())
                .images(imageDtos)
                .tags(restaurantTagDtos)
                .regularHolidays(regularHolidayDtos)
                .build();
    }


    @Transactional
    public Long createRestaurant(RestaurantCreateRequest request) {
        // 1. Restaurant 엔티티 생성 및 저장
        // TODO: 추후 인증(Authentication) 도입 시 실제 ownerId 사용
        long currentOwnerId = 1L;

        Restaurant restaurant = Restaurant.builder()
                .ownerId(currentOwnerId)
                .name(request.getName())
                .phone(request.getPhone())
                .roadAddress(request.getRoadAddress())
                .detailAddress(request.getDetailAddress())
                .description(request.getDescription())
                .status(RestaurantStatus.OPEN) // 초기 상태는 OPEN
                .avgMainPrice(0) // 메뉴가 없으므로 초기값은 0
                .reservationLimit(request.getReservationLimit())
                .holidayAvailable(request.isHolidayAvailable())
                .preorderAvailable(request.isPreorderAvailable())
                .openTime(LocalTime.parse(request.getOpenTime()))
                .closeTime(LocalTime.parse(request.getCloseTime()))
                .openDate(request.getOpenDate())
                .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        Long newRestaurantId = savedRestaurant.getRestaurantId();

        // 2. 이미지 정보 저장은 ObjectStorage와 연동되어 추후 구현 예정이므로 현재는 건너뜀
        // if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
        //     request.getImageUrls().forEach(imageUrl -> {
        //         RestaurantImage image = RestaurantImage.builder()
        //                 .restaurant(savedRestaurant)
        //                 .imageUrl(imageUrl)
        //                 .build();
        //         restaurantImageRepository.save(image);
        //     });
        // }

        // 3. 식당 메뉴 정보 저장 (추후 구현 예정)

        // 4. 정기 휴무일 정보 저장
        if (request.getRegularHolidayNumbers() != null && !request.getRegularHolidayNumbers().isEmpty()) {
            request.getRegularHolidayNumbers().forEach(dayOfWeek -> {
                RegularHoliday holiday = RegularHoliday.builder()
                        .restaurantId(newRestaurantId)
                        .dayOfWeek(dayOfWeek)
                        .build();
                regularHolidayRepository.save(holiday);
            });
        }

        // 5. 태그 정보 저장
        if (request.getSelectedTagIds() != null && !request.getSelectedTagIds().isEmpty()) {
            request.getSelectedTagIds().forEach(tagId -> {
                restaurantRepository.saveRestaurantTagMapping(newRestaurantId, tagId);
            });
        }

        return newRestaurantId;
    }
}
