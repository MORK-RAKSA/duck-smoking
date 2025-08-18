package com.raksa.app.controllers;

import com.raksa.app.dtos.requests.GetOTPRequestDto;
import com.raksa.app.dtos.requests.LoginRequestDto;
import com.raksa.app.dtos.requests.UserRegisterRequestDto;
import com.raksa.app.dtos.requests.VerifyRequestDto;
import com.raksa.app.exception.ResponseMessage;
import com.raksa.app.services.servicesImpl.UserRegisterImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserRegisterController {
    private final UserRegisterImpl userRegister;

    @PostMapping("/v2/user-register")
    public Mono<ResponseMessage<Object>> registerUser(@RequestBody UserRegisterRequestDto requestDto){
        return userRegister.registerUser(requestDto);
    }

    @PostMapping("/v2/user-email-otp")
    public Mono<ResponseMessage<Object>> getOTP(@RequestBody GetOTPRequestDto requestDto) throws MessagingException, IOException {
        return userRegister.getOTP(requestDto);
    }

    @PostMapping("/v2/user-login")
    public Mono<ResponseMessage<Object>> userLogin(@RequestBody @Valid LoginRequestDto requestDto){
        return userRegister.loginUser(requestDto);
    }

    @PostMapping("/v2/verify-otp")
    public Mono<ResponseMessage<Object>> verify(@RequestBody VerifyRequestDto requestDto){
        return userRegister.verifyUser(requestDto);
    }

    @GetMapping("/get-cookie-testing")
    @ResponseBody
    public String profile(@CookieValue(name = "Idea-91e1f3d3", required = false) String myId) {
        return "Your id is " + myId;
    }
}