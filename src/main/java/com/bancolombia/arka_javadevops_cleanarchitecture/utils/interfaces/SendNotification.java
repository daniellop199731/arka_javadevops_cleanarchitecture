package com.bancolombia.arka_javadevops_cleanarchitecture.utils.interfaces;

public interface SendNotification {

    void sendNotificationToEmail(String message, String addressee);

    void sendNotificationToPhone(String message, String number);

}
