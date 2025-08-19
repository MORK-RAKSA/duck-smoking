package com.raksa.app.services.impls;

import com.raksa.app.Repository.UserRepository;
import com.raksa.app.enums.AuthProvider;
import com.raksa.app.model.AuthUser;
import com.raksa.app.utils.LoggerFormaterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

//@Component
//public class CustomOAuth2UserService
//        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
//    private final UserRepository users;
//
//    public CustomOAuth2UserService(UserRepository users) {
//        this.users = users;
//    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
//        OAuth2User oauth2User = delegate.loadUser(req);
//
//        // Google returns "sub" as stable user id; also "email", "name", "picture"
//        Map<String, Object> a = oauth2User.getAttributes();
//        String sub = (String) a.get("sub");
//        String email = (String) a.get("email");
//        String name = (String) a.getOrDefault("name", email);
//        String picture = (String) a.getOrDefault("picture", "");
//
//        AuthUser user = users.findByProviderId(sub).orElseGet(() -> {
//            AuthUser u = new AuthUser();
//            u.setProvider(AuthProvider.GOOGLE);
//            u.setProviderId(sub);
//            u.setEmail(email);
//            u.setName(name);
//            u.setPictureUrl(picture);
//            return u;
//        });
//
//        user.setName(name);
//        user.setPictureUrl(picture);
//        user.setLastLoginAt(LocalDateTime.now());
//        users.save(user);
//
//        LoggerFormaterUtils.convertDtoToJson("Gmail ", user);
//        // Expose authorities minimally
//        Collection<GrantedAuthority> auth =
//
//                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
//
//        // Wrap with DefaultOAuth2User to keep attributes if needed
//        return new DefaultOAuth2User(auth, a, "sub");
//    }
//}






@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = delegate.loadUser(userRequest);
        log.info("Google attributes: {}", user.getAttributes());
        return user;
    }
}
