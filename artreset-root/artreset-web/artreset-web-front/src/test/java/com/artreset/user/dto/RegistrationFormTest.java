package com.artreset.user.dto;

import com.artreset.model.SocialMediaService;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Taehyun Jung
 */
public class RegistrationFormTest {

    private static final SocialMediaService SIGN_IN_PROVIDER = SocialMediaService.TWITTER;

    @Test
    public void isNormalRegistration_SocialProviderNotSet_ShouldReturnTrue() {
        RegistrationForm dto = new RegistrationFormBuilder().build();

        boolean isNormalRegistration = dto.isNormalRegistration();

        assertThat(isNormalRegistration).isTrue();
    }

    @Test
    public void isNormalRegistration_SocialProviderSet_ShouldReturnFalse() {
        RegistrationForm dto = new RegistrationFormBuilder()
                .signInProvider(SIGN_IN_PROVIDER)
                .build();

        boolean isNormalRegistration = dto.isNormalRegistration();

        assertThat(isNormalRegistration).isFalse();
    }

    @Test
    public void isSocialSignIn_SocialProviderNotSet_ShouldReturnFalse() {
        RegistrationForm dto = new RegistrationFormBuilder().build();

        boolean isSocialSignIn = dto.isSocialSignIn();

        assertThat(isSocialSignIn).isFalse();
    }

    @Test
    public void isSocialSignIn_SocialProviderSet_ShouldReturnTrue() {
        RegistrationForm dto = new RegistrationFormBuilder()
                .signInProvider(SIGN_IN_PROVIDER)
                .build();

        boolean isSocialSignIn = dto.isSocialSignIn();

        assertThat(isSocialSignIn).isTrue();
    }
}
