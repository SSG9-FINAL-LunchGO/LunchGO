package com.example.LunchGo.sms.service;

import com.example.LunchGo.reservation.dto.OwnerReservationNotification;

import java.util.HashMap;

public interface SmsService {
    String createVerifyCode();

    void certificateSMS(String phone);

    Boolean verifySMSCode(String phone, String code);

    void sendNotificationToOwner(String phone, OwnerReservationNotification notification);
}
