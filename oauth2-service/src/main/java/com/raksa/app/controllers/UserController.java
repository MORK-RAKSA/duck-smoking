package com.raksa.app.controllers;

import com.raksa.app.dto.UserResponseDto;
import com.raksa.app.exception.ResponseMessage;
//import com.raksa.app.services.impls.OAuth2Service;
import com.raksa.app.mapper.AuthUserMapper;
import com.raksa.app.services.impls.OAuth2Service;
import com.raksa.app.utils.LoggerFormaterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/user")
    public ResponseMessage<Principal> getUser(Principal principal) {
        if (principal == null) {
            return ResponseMessage.error("No user is authenticated");
        }
        LoggerFormaterUtils.convertDtoToJson("Principal", principal);
        return ResponseMessage.success(principal);
    }

    @GetMapping("/get-user-info")
    public ResponseMessage<List<UserResponseDto>> getUser(){
        List<UserResponseDto> userResponseDto = oAuth2Service.getAllUsers();
        return ResponseMessage.success(userResponseDto);
    }

}
