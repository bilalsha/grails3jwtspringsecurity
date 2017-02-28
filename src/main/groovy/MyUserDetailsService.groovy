import com.jwt.security.User
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import org.springframework.dao.DataAccessException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.transaction.annotation.Transactional

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
class MyUserDetailsService implements GrailsUserDetailsService{

    static final List NO_ROLES = [new SimpleGrantedAuthority(SpringSecurityUtils.NO_ROLE)]

    @Override
    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException, DataAccessException {
        return null
    }

    @Override
    @Transactional(readOnly=true, noRollbackFor=[IllegalArgumentException, UsernameNotFoundException])
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = User.findByUsername(username)
        if (!user) throw new NoStackUsernameNotFoundException()

        def roles = user.authorities

        // or if you are using role groups:
        // def roles = user.authorities.collect { it.authorities }.flatten().unique()

        def authorities = roles.collect {
            new SimpleGrantedAuthority(it.authority)
        }

        return new MyUserDetails(user.username, user.password, user.enabled,
                !user.accountExpired, !user.passwordExpired,
                !user.accountLocked, authorities ?: NO_ROLES, user.id)
    }
}
