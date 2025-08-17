package com.raksa.app.dtos.requests;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
