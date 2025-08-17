package com.raksa.app.dtos.requests;

import lombok.Data;

@Data
public class VerifyRequestDto {
    private String email;
    private String code;
}
