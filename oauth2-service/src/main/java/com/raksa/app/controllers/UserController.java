package com.raksa.app.controllers;

import com.raksa.app.dto.RefreshRequest;
import com.raksa.app.dto.UserResponseDto;
import com.raksa.app.exception.ResponseMessage;
//import com.raksa.app.services.impls.OAuth2Service;
import com.raksa.app.services.impls.OAuth2Service;
import com.raksa.app.services.jwt.JwtService;
import com.raksa.app.utils.LoggerFormaterUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final OAuth2Service oAuth2Service;
    private final JwtService jwtService;

    @GetMapping("/user")
    public ResponseMessage<Principal> getUser(Principal principal) {
        if (principal == null) {
            return ResponseMessage.error("No user is authenticated");
        }
        LoggerFormaterUtils.convertDtoToJson("Principal", principal);
        return ResponseMessage.success(principal);
    }
//
//    @GetMapping("/get-user-info")
//    public ResponseMessage<List<UserResponseDto>> getUser(){
//        List<UserResponseDto> userResponseDto = oAuth2Service.getAllUsers();
//        return ResponseMessage.success(userResponseDto);
//    }

    @PostMapping("/refresh")
    public ResponseMessage<String> refresh(@RequestBody RefreshRequest request){
        Claims claims = jwtService.extractClaims(request.getRefreshToken());
        String userId = claims.get("id", String.class);

        UserResponseDto user = oAuth2Service.getUserById(userId);

        String newAccessToken = jwtService.generateAccessToken(user);
        return ResponseMessage.success("Token refreshed successfully", newAccessToken);
    }
}
