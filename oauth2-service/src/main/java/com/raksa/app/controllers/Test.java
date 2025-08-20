package com.raksa.app.controllers;

import com.raksa.app.Repository.UserRepository;
import com.raksa.app.exception.ResponseMessage;
import com.raksa.app.model.AuthUser;
import com.raksa.app.services.impls.OAuth2Service;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class Test {

    private final UserRepository userRepository;
    private final OAuth2Service oAuth2Service;

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
        oAuth2Service.saveProfile(token, model);
        return "profile";
    }

}
