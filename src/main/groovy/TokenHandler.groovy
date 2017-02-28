import com.jwt.security.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetailsService

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
class TokenHandler {

    private String secret
    private UserDetailsService userService

    public User parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
        return userService.loadUserByUsername(username)
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    String getSecret() {
        return secret
    }

    void setSecret(String secret) {
        this.secret = secret
    }

    UserDetailsService getUserService() {
        return userService
    }

    void setUserService(UserDetailsService userService) {
        this.userService = userService
    }
}
