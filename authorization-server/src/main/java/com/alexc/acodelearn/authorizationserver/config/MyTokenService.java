package com.alexc.acodelearn.authorizationserver.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

class MyTokenService extends DefaultTokenServices {

        public MyTokenService() {
        }

        @Override
        public OAuth2AccessToken readAccessToken(String accessToken) {
            return super.readAccessToken(accessToken);
        }

        @Override
        public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
            OAuth2AccessToken token = super.createAccessToken(authentication);
            // Account account = (Account) authentication.getPrincipal();
            // This is where I will add my logic when it hits the breakpoint.
            return token;
        }

        @Override
        public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
                throws AuthenticationException {

            OAuth2AccessToken token = super.refreshAccessToken(refreshTokenValue, tokenRequest);
            return token;
        }

    @Override
    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        super.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
    }
}