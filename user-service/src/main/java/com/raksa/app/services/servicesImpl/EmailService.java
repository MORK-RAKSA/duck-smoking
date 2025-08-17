package com.raksa.app.services.servicesImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final Random random = new Random();

    public String sendOTP(String toEmail) {
        String otp = String.format("%06d", random.nextInt(1000000));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\nIt expires in 2 minutes.");
        message.setFrom("raksamork097@gmail.com");
        mailSender.send(message);

        return otp;
    }


    public String sendOtpEmail(String toEmail) throws MessagingException, IOException {

        String otp = String.format("%06d", random.nextInt(1_000_000));

        ClassPathResource resource = new ClassPathResource("templates/otp-template.html");

        // Read the HTML file from classpath
        String htmlContent;
        try (InputStream inputStream = resource.getInputStream()) {
            htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }

        // Replace placeholder
        htmlContent = htmlContent.replace("{{OTP}}", otp);
        htmlContent = htmlContent.replace("{{TO_EMAIL}}", toEmail);
        htmlContent = htmlContent.replace("{{VERIFY_URL}}", "https://duck-smoking--stage.loca.lt/verify?otp=" + otp);

        // Send email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(toEmail);
        helper.setSubject("Your OTP Code");
        helper.setText(htmlContent, true);

        mailSender.send(message);
        return otp;
    }

}
