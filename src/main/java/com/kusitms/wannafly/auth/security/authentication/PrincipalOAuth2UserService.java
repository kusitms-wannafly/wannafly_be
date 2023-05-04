package com.kusitms.wannafly.auth.security.authentication;

import com.kusitms.wannafly.auth.application.AuthService;
import com.kusitms.wannafly.auth.dto.LoginRequest;
import com.kusitms.wannafly.auth.dto.LoginResponse;
import com.kusitms.wannafly.auth.security.oauth.OAuth2Member;
import com.kusitms.wannafly.auth.security.oauth.RegistrationId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final AuthService authService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        LoginRequest loginRequest = toLoginRequest(userRequest);
        LoginResponse loginResponse = authService.login(loginRequest);
        return new OAuth2Member(
                loginResponse.memberId(),
                loginResponse.accessToken(),
                loginResponse.refreshToken()
        );
    }

    private LoginRequest toLoginRequest(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        RegistrationId registrationId = RegistrationId.from(
                userRequest.getClientRegistration().getRegistrationId()
        );
        return new LoginRequest(
                registrationId.getValue(),
                oAuth2User.getAttribute(registrationId.getNameAttribute()),
                oAuth2User.getAttribute(registrationId.getEmailAttribute()),
                oAuth2User.getAttribute(registrationId.getPictureUrlAttribute())
        );
    }
}
