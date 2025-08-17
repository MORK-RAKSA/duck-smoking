//package com.raksa.app.services.servicesImpl;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import com.twilio.type.PhoneNumber;
//
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//public class PhoneNumberOTPServie {
//
//    private final Random random = new Random();
//
//    @Value("${twilio.account-sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth-token}")
//    private String authToken;
//
////    @Value("${twilio.phone-number}")
////    private String fromNumber;
//
//    public String sendOtp(String toNumber) {
//        String otp = String.format("%06d", random.nextInt(1000000));
//        Twilio.init(accountSid, authToken);
//        Message.creator(
//                new PhoneNumber(toNumber),
//                new PhoneNumber("+85561460062"),
//                "Your OTP is: " + otp
//        ).create();
//        return otp;
//    }
//
//    public String sendSms(String toNumber, String messageBody) {
//        Message message = Message.creator(
//                new PhoneNumber(toNumber),
//                new PhoneNumber("+85561460062"),
//                messageBody
//        ).create();
//
//        return message.getSid();
//    }
//}
