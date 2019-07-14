package com.alexc.acodelearn.authorizationserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class TokenController {

    Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;

    @RequestMapping(method = RequestMethod.GET, value = "/tokens")
    @ResponseBody
    public List<String> getTokens() {
        List<String> tokenValues = new ArrayList<String>();
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("uiPlatformClient");
        tokens.addAll(tokenStore.findTokensByClientId("fooClientIdPassword"));
        if (tokens != null){
            for (OAuth2AccessToken token:tokens){
                tokenValues.add(token.getValue());
            }
            logger.info(tokenValues.toString());
        }
        logger.info(tokenValues.toString());
        return tokenValues;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tokens/revoke/{tokenId:.*}")
    @ResponseBody
    public String revokeToken(@PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
        return tokenId;
    }

}
