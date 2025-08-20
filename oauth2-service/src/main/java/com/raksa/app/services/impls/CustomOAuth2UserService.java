//package com.raksa.app.services.impls;
//
//import com.raksa.app.Repository.UserRepository;
//import com.raksa.app.model.AuthUser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final UserRepository userRepository;
//    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        String email = oAuth2User.getAttribute("email");
//        String name  = oAuth2User.getAttribute("name");
//        String sub   = oAuth2User.getAttribute("sub");
//
//        if (email == null) {
//            throw new OAuth2AuthenticationException("Email not found in provider response");
//        }
//
//        AuthUser user = userRepository.findByEmail(email).orElseGet(AuthUser::new);
//        user.setEmail(email);
//        user.setName(name);
//        user.setProviderId(sub);
//        userRepository.save(user);
//
//        // Wrap attributes into a Spring Security user
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                oAuth2User.getAttributes(),
//                "email"
//        );
//    }
//}
//
