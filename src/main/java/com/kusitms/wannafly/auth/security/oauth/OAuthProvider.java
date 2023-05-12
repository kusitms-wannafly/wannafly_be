package com.kusitms.wannafly.auth.security.oauth;

import com.kusitms.wannafly.auth.dto.LoginRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public enum OAuthProvider {

    GOOGLE {
        @Override
        public LoginRequest toLoginRequest(OAuth2User oAuth2User) {
            return new LoginRequest(
                    name().toLowerCase(),
                    oAuth2User.getAttribute("name"),
                    oAuth2User.getAttribute("email"),
                    oAuth2User.getAttribute("picture")
            );
        }
    },
    KAKAO {
        @Override
        public LoginRequest toLoginRequest(OAuth2User oAuth2User) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, String> properties = (Map<String, String>) attributes.get("properties");
            return new LoginRequest(
                    name().toLowerCase(),
                    properties.get("nickname"),
                    (String) kakaoAccount.get("email"),
                    properties.get("profile_image")
            );
        }
    },
    NAVER {
        @Override
        public LoginRequest toLoginRequest(OAuth2User oAuth2User) {
            Map<String, String> response = oAuth2User.getAttribute("response");
            return new LoginRequest(
                    name().toLowerCase(),
                    response.get("name"),
                    response.get("email"),
                    response.get("profile_image")
            );
        }
    }

    ;

    public abstract LoginRequest toLoginRequest(OAuth2User oAuth2User);

    public static OAuthProvider from(String registrationId) {
        return valueOf(registrationId.toUpperCase());
    }
}
