package com.raksa.app.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
