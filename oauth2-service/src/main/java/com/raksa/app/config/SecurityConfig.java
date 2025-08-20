package com.raksa.app.config;

//import com.raksa.app.services.impls.CustomOAuth2UserService;
import com.raksa.app.services.impls.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2Service auth2Service;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(auth2Service))
                );
        return http.build();
    }

}
