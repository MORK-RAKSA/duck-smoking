package com.raksa.app.controllers;


import com.raksa.app.exception.ResponseMessage;
import com.raksa.app.utils.LoggerFormaterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class Test1Controller {

    @GetMapping("/user")
    public ResponseMessage<Principal> getUser(Principal principal) {
        LoggerFormaterUtils.convertDtoToJson("Principal", principal);
        return ResponseMessage.success(principal);
    }

}
