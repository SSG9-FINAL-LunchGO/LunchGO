package com.example.LunchGo.reservation.controller;

import com.example.LunchGo.account.dto.CustomUserDetails;
import com.example.LunchGo.reservation.dto.CancelReservationRequest;
import com.example.LunchGo.reservation.dto.CancelReservationResponse;
import com.example.LunchGo.reservation.dto.ReservationCreateRequest;
import com.example.LunchGo.reservation.dto.ReservationCreateResponse;
<<<<<<< Updated upstream
import com.example.LunchGo.reservation.dto.ReservationHistoryItem;
import com.example.LunchGo.reservation.service.ReservationCompletionService;
import com.example.LunchGo.reservation.service.ReservationHistoryService;
=======
import com.example.LunchGo.reservation.dto.UserReservationDetailResponse;
import com.example.LunchGo.reservation.dto.UserReservationResponse;
>>>>>>> Stashed changes
import com.example.LunchGo.reservation.service.ReservationService;
import com.example.LunchGo.reservation.service.UserReservationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
<<<<<<< Updated upstream

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationHistoryService reservationHistoryService;
    private final ReservationCompletionService reservationCompletionService;

    @PostMapping
    public ResponseEntity<ReservationCreateResponse> create(@RequestBody ReservationCreateRequest request) {
        try {
            ReservationCreateResponse response = reservationService.create(request);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/slots")
    public ResponseEntity<List<LocalTime>> slotTimes(
            @RequestParam Long restaurantId,
            @RequestParam LocalDate slotDate
=======

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;                 // (기존) 예약 생성/슬롯
    private final UserReservationQueryService userReservationQueryService; // (추가) 사용자 조회

    // 예약 생성
    @PostMapping
    public ResponseEntity<ReservationCreateResponse> create(@RequestBody ReservationCreateRequest request) {
        ReservationCreateResponse response = reservationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 슬롯 시간 조회
    @GetMapping("/slots")
    public ResponseEntity<List<LocalTime>> slotTimes(
            @RequestParam Long restaurantId,
            @RequestParam LocalDate slotDate
    ) {
        return ResponseEntity.ok(reservationService.slotTimes(restaurantId, slotDate));
    }

    /**
     * 사용자 예약 내역 (본인 것만)
     * 예) GET /api/reservations/my
     */
    @GetMapping("/my")
    public ResponseEntity<List<UserReservationResponse>> myReservations(
            @AuthenticationPrincipal CustomUserDetails userDetails
>>>>>>> Stashed changes
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userReservationQueryService.getMyReservations(userDetails.getId()));
    }

    /**
     * 사용자 예약 상세 (본인 것만)
     * 예) GET /api/reservations/10?userId=3
     */
    @GetMapping("/{reservationId}")
    public ResponseEntity<UserReservationDetailResponse> myReservationDetail(
            @PathVariable Long reservationId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(userReservationQueryService.getMyReservationDetail(userId, reservationId));
    }

    /**
     * 사용자 예약 취소 (ReservationCancelView.vue가 호출)
     * POST /api/reservations/{id}/cancel
     * 응답은 { ok: true } 형태로 내려준다.
     */
    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<CancelReservationResponse> cancel(
            @PathVariable Long reservationId,
            @RequestBody CancelReservationRequest request
    ) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/history")
    public ResponseEntity<List<ReservationHistoryItem>> history(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "past") String type
    ) {
        List<ReservationHistoryItem> items = reservationHistoryService.getHistory(userId, type);
        return ResponseEntity.ok(items);
    }

    @PatchMapping("/{reservationId}/complete")
    public ResponseEntity<Void> complete(@PathVariable Long reservationId) {
        reservationCompletionService.complete(reservationId);
        return ResponseEntity.noContent().build();
    }
}
