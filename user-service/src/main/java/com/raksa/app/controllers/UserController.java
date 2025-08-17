package com.raksa.app.controllers;

import com.raksa.app.dtos.requests.UserRequestDto;
import com.raksa.app.dtos.responses.UserResponseDto;
import com.raksa.app.exception.ResponseMessage;
import com.raksa.app.services.servicesImpl.EmailService;
//import com.raksa.app.services.servicesImpl.PhoneNumberOTPServie;
import com.raksa.app.services.servicesImpl.UserServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    private final UserServiceImpl userService;
    private final EmailService emailService;
//    private final PhoneNumberOTPServie phoneNumberOTPServie;

    @PostMapping("/create-user")
    public ResponseMessage<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseMessage.success("User Created Successfully.", responseDto);
    }

    @GetMapping("/get-all-users")
    public ResponseMessage<List<UserResponseDto>> getAllUsers() {
        return ResponseMessage.success("Fetch Users Successfully.", userService.getAllUser());
    }

    @PostMapping("/delete-all-users")
    public ResponseMessage<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseMessage.success("All Users Deleted Successfully.");
    }

    @PostMapping("/delete-all-users1")
    public ResponseMessage<Void> deleteAllUsers1() {
        userService.deleteAll();
        return ResponseMessage.success("All Users Deleted Successfully.");
    }

    @GetMapping("/get-user-by-id")
    public ResponseMessage<UserResponseDto> getUserById(@RequestParam String id) {
        UserResponseDto responseDto = userService.getUserById(id);
        return ResponseMessage.success("Fetch User Successfully.", responseDto);
    }

    @GetMapping("/get-user-by-username")
    public ResponseMessage<UserResponseDto> getUserByUsername(@RequestParam String username) {
        UserResponseDto responseDto = userService.getUserByUsername(username);
        return ResponseMessage.success("Fetch User Successfully.", responseDto);
    }

    @GetMapping("/get-product-testing")
    public Mono<ResponseMessage<String>> getProductTesting() {
        return userService.getProductTesting();
    }


    @PostMapping("/send-otp/{toEmail}")
    public ResponseMessage<String> sendOtp(@PathVariable String toEmail) throws MessagingException, IOException {
        String otp = emailService.sendOtpEmail(toEmail);
        return ResponseMessage.success("OTP sent successfully.", otp);
    }


//    @PostMapping("/v2/send-otp")
//    public ResponseMessage <String> sendOtpToPhoneNumber(@RequestParam String phoneNumber) throws MalformedURLException {
//        String otp = phoneNumberOTPServie.sendOtp(phoneNumber);
//        return ResponseMessage.success("OTP sent successfully.", otp);
//
//    }
//
//    @PostMapping("/send")
//    public String sendSms(@RequestParam String to, @RequestParam String body) {
//        String sid = phoneNumberOTPServie.sendSms(to, body);
//        return "SMS sent! Message SID: " + sid;
//    }
}