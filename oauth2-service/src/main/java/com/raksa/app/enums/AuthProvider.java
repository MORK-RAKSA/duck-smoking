package com.raksa.app.enums;

public enum AuthProvider {
    GOOGLE, GITHUB, FACEBOOK, OTHER;

    public static AuthProvider fromRegistrationId(String registrationId) {
        try {
            return AuthProvider.valueOf(registrationId.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }
}
