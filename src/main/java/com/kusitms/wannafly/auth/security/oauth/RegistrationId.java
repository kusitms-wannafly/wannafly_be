package com.kusitms.wannafly.auth.security.oauth;

import lombok.Getter;

@Getter
public enum RegistrationId {

    GOOGLE("name", "email", "picture");

    private final String nameAttribute;
    private final String emailAttribute;
    private final String pictureUrlAttribute;

    RegistrationId(String nameAttribute, String emailAttribute, String pictureUrlAttribute) {
        this.nameAttribute = nameAttribute;
        this.emailAttribute = emailAttribute;
        this.pictureUrlAttribute = pictureUrlAttribute;
    }

    public static RegistrationId from(String registrationId) {
        return valueOf(registrationId.toUpperCase());
    }

    public String getValue() {
        return name().toLowerCase();
    }
}
