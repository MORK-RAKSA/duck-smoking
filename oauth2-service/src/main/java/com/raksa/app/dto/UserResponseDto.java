package com.raksa.app.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private String id;
    private String provider;
    private String providerId;
    private String name;
    private String email;
    private String picture;

}
