package com.raksa.app.exceptions;

public class OAuth2LoginSuccessHandler extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OAuth2LoginSuccessHandler(String message) {
        super(message);
    }

    public OAuth2LoginSuccessHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuth2LoginSuccessHandler(Throwable cause) {
        super(cause);
    }
}