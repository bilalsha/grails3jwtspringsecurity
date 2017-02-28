import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.GrantedAuthority

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
class MyUserDetails extends GrailsUser{

    MyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities, Object id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, id)
    }
}
