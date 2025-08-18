package com.raksa.app.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.List;

@Configuration
public class UserClient {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://duck-smoking-api-dev.loca.lt/")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true) // follow 3xx if your server redirects after login
                ))
                .filter((request, next) -> next.exchange(request).doOnNext(res -> {
                    // Debug: see Set-Cookie headers
                    List<String> setCookies = res.headers().asHttpHeaders().get("Set-Cookie");
                    if (setCookies != null) {
                        setCookies.forEach(v ->{
                            // Print each Set-Cookie header
                            if (v != null && !v.isEmpty()) {
                                System.out.println("Set-Cookie: " + v);
                            }
                        });
                    }
                }))
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
