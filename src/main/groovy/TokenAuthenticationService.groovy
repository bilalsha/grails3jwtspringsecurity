import com.jwt.security.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private String secret;
    private TokenHandler tokenHandler;

    public void addAuthentication(HttpServletResponse response, Authentication authentication) {
        final User user = authentication.getDetails();
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final User user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

    String getSecret() {
        return secret
    }

    void setSecret(String secret) {
        this.secret = secret
    }

    TokenHandler getTokenHandler() {
        return tokenHandler
    }

    void setTokenHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler
    }
}
