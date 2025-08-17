package com.raksa.app.services.servicesImpl;

import com.raksa.app.clients.UserClient;
import com.raksa.app.dtos.requests.GetOTPRequestDto;
import com.raksa.app.dtos.requests.LoginRequestDto;
import com.raksa.app.dtos.requests.UserRegisterRequestDto;
import com.raksa.app.dtos.requests.VerifyRequestDto;
import com.raksa.app.exception.ResponseMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UserRegisterImpl {

    private final WebClient webClient;
    private final EmailService emailService;
    private final JavaMailSender mailSender;

    public Mono<ResponseMessage<Object>> registerUser(UserRegisterRequestDto requestDto) {

        // Validate email format
        if (!isValidGmailFormat(requestDto.getEmail())) {
            return Mono.just(ResponseMessage.error("Invalid Gmail format"));
        }

        return webClient.post()
                .uri("/api/v1/register")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseMessage<Object>>() {})
                .onErrorResume(e ->
                        Mono.just(ResponseMessage.error(
                                "Fetching user registration failed: " + e.getMessage()
                        ))
                );
    }

    public Mono<ResponseMessage<Object>> getOTP(GetOTPRequestDto requestDto) throws MessagingException, IOException {

        emailService.sendOtpEmail(requestDto.getEmail());

        return webClient.post()
                .uri("/dev/api/v1/getOtp")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseMessage<Object>>() {})
                .onErrorResume(e -> Mono.just(ResponseMessage.error("Fetching OTP failed: " + e.getMessage())));
    }

    public Mono<ResponseMessage<Object>> loginUser(LoginRequestDto requestDto) {
        return webClient.post()
                .uri("/api/v1/login")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseMessage<Object>>() {})
                .onErrorResume(e -> Mono.just(ResponseMessage.error("Fetching Endpoint failed: " + e.getMessage())));
    }

    public Mono<ResponseMessage<Object>> verifyUser(VerifyRequestDto requestDto) {
        return webClient.post()
                .uri("/api/v1/verify")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseMessage<Object>>() {})
                .onErrorResume(e -> Mono.just(ResponseMessage.error("Fetching Endpoint failed: " + e.getMessage())));
    }

    private boolean isValidGmailFormat(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email != null && email.matches(regex);
    }

}
