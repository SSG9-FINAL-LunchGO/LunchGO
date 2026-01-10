package com.example.LunchGo.reservation.service;

import com.example.LunchGo.reservation.domain.ReservationStatus;
import com.example.LunchGo.reservation.entity.Payment;
import com.example.LunchGo.reservation.entity.Reservation;
import com.example.LunchGo.reservation.repository.PaymentRepository;
import com.example.LunchGo.reservation.repository.ReservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationPaymentExpiryService {
    private static final String PAYMENT_STATUS_FAILED = "FAILED";

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public int expireDuePayments(LocalDateTime now) {
        List<Reservation> targets = reservationRepository.findDueForPaymentExpiry(
            ReservationStatus.TEMPORARY,
            now
        );
        if (targets == null || targets.isEmpty()) {
            return 0;
        }

        int expired = 0;
        for (Reservation reservation : targets) {
            if (reservation == null || reservation.getReservationId() == null) {
                continue;
            }
            if (!ReservationStatus.TEMPORARY.equals(reservation.getStatus())) {
                continue;
            }
            reservation.setStatus(ReservationStatus.EXPIRED);
            log.info("결제 만료 처리: reservationId={}, holdExpiresAt={}, paymentDeadlineAt={}, now={}",
                reservation.getReservationId(),
                reservation.getHoldExpiresAt(),
                reservation.getPaymentDeadlineAt(),
                now);
            paymentRepository.findTopByReservationIdOrderByCreatedAtDesc(reservation.getReservationId())
                .ifPresent(payment -> markPaymentFailed(payment, now));
            expired++;
        }
        return expired;
    }

    private void markPaymentFailed(Payment payment, LocalDateTime now) {
        if (payment == null) {
            return;
        }
        payment.setStatus(PAYMENT_STATUS_FAILED);
        payment.setFailedAt(now);
    }
}
