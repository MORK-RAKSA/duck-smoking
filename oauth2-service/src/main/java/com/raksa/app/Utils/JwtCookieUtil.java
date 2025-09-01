package com.raksa.app.Utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class JwtCookieUtil {

    public static void addJwtToCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);        // prevent JavaScript access
        cookie.setSecure(true);          // use only with HTTPS
        cookie.setPath("/");             // cookie available to entire app
        cookie.setMaxAge(30 * 24 * 60 * 60);  // 1 day
        response.addCookie(cookie);
    }
}