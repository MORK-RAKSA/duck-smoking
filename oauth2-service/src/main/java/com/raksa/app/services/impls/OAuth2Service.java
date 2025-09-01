package com.raksa.app.services.impls;

import com.raksa.app.Repository.UserRepository;
import com.raksa.app.dto.AuthResponse;
import com.raksa.app.exception.exceptionHandle.ResourceNotFoundException;
import com.raksa.app.services.jwt.JwtService;
import com.raksa.app.dto.UserResponseDto;
import com.raksa.app.mapper.AuthUserMapper;
import com.raksa.app.model.AuthUser;
import com.raksa.app.utils.LoggerFormaterUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    private final AuthUserMapper authUserMapper;
    private final JwtService jwtService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getName();
        String name = (String) oAuth2User.getAttributes().get("name");
        String email = (String) oAuth2User.getAttributes().get("email");
        String picture = (String) oAuth2User.getAttributes().get("picture");

        log.info("\n\n====== Name: {}, Email: {}, Picture: {} ======\n\n\n", name, email, picture);

        System.out.println(">>> OAUTH2 USER = " + oAuth2User.getAttributes());

        userRepository.findByEmail(email).ifPresentOrElse(
                u -> {
                    u.setName(name);
                    u.setPicture(picture);
                    userRepository.save(u);
                },
                () -> {
                    AuthUser u = new AuthUser();
                    u.setProvider(provider);
                    u.setProviderId(providerId);
                    u.setName(name);
                    u.setEmail(email);
                    u.setPicture(picture);
                    userRepository.save(u);
                    System.out.println(">>> NEW USER SAVED: " + email);
                }
        );

        return oAuth2User;
    }

    public void saveProfile(OAuth2AuthenticationToken token) {
        var principal = token.getPrincipal();
        Map<String, Object> attributes = principal.getAttributes();

        String provider = token.getAuthorizedClientRegistrationId();
        String providerId = principal.getName();
        String name = (String) attributes.get("name");
        String email = attributes.get("email") != null
                ? (String) attributes.get("email")   // Google
                : (String) attributes.get("login");  // GitHub
        String picture = attributes.get("picture") != null
                ? (String) attributes.get("picture")   // Google
                : (String) attributes.get("avatar_url"); // GitHub

        // Save or update user
        AuthUser user = userRepository.findByEmail(email)
                .map(existing -> {
                    existing.setName(name);
                    existing.setPicture(picture);
                    return userRepository.save(existing);
                })
                .orElseGet(() -> {
                    AuthUser u = new AuthUser();
                    u.setProvider(provider);
                    u.setProviderId(providerId);
                    u.setName(name);
                    u.setEmail(email);
                    u.setPicture(picture);
                    return userRepository.save(u);
                });

        UserResponseDto responseDto = authUserMapper.toResponseDto(user);
        responseDto.setName(user.getName());
        responseDto.setPicture(user.getPicture());
        responseDto.setProviderId(user.getProviderId());
        responseDto.setProvider(user.getProvider());
        responseDto.setEmail(user.getEmail());

        LoggerFormaterUtils.convertDtoToJson("User Response Dto", responseDto);

        String jwt = jwtService.generateAccessToken(responseDto);
        log.info("\n\n\nGenerated JWT: {}\n\n", jwt);
    }

    public UserResponseDto getUserById(String id) {
        AuthUser entity = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(
                String.format("No user found with ID: %s.", id)));
        return authUserMapper.toResponseDto(entity);
    }

}