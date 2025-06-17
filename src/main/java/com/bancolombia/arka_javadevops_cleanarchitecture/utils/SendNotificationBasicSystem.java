package com.bancolombia.arka_javadevops_cleanarchitecture.utils;

import org.springframework.context.annotation.Primary;

import com.bancolombia.arka_javadevops_cleanarchitecture.utils.interfaces.SendNotification;

@Primary
public class SendNotificationBasicSystem implements SendNotification{

    @Override
    public void sendNotificationToEmail(String message, String addressee) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'sendNotificationEmail'");
        System.out.println(message.concat(". to ").concat(addressee));
    }

    @Override
    public void sendNotificationToPhone(String message, String number) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'sendNotificationPhone'");
        System.out.println(message.concat(". to ").concat(number));
    }

    

}
