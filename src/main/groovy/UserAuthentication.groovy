import com.jwt.security.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
class UserAuthentication implements Authentication {

    private final User user;
    private boolean authenticated = true;

    public UserAuthentication(User user) {
        this.user = user;
    }

    public String getName() {
        return user.getUsername();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    public Object getCredentials() {
        return user.getPassword();
    }

    public User getDetails() {
        return user;
    }

    public Object getPrincipal() {
        return user.getUsername();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
