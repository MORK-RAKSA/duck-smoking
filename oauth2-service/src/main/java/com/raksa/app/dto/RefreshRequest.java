package com.raksa.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}