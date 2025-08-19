package com.raksa.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class Test {

    @GetMapping("/")
    public String profileUser(OAuth2AuthenticationToken token, Model model) {
        var principal = token.getPrincipal();
        String provider = token.getAuthorizedClientRegistrationId();

        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("email", principal.getAttribute("email"));
        model.addAttribute("photo", principal.getAttribute("picture") != null
                ? principal.getAttribute("picture")   // Google
                : principal.getAttribute("avatar_url") // GitHub
        );
        model.addAttribute("provider", provider.substring(0,1).toUpperCase() + provider.substring(1));

        return "profile";
    }

}
