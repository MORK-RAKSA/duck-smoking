package com.raksa.app.controllers;


import com.raksa.app.exception.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class Test {

    @GetMapping("/")
    public ResponseMessage<String> test() {
        return ResponseMessage.success("Hello, World! from Product Testing Service");
    }

}
