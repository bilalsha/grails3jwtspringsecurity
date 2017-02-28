package grails3jwtspringsecurity

import com.jwt.security.Role
import com.jwt.security.User
import com.jwt.security.UserRole
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils

class BootStrap {

    def init = { servletContext ->
        User user = new User(username: 'bilal', password: 'shah', accountLocked: false, accountExpired: false).save()
        Role role = new Role(authority: 'ROLE_ADMIN').save()
        UserRole userRole = new UserRole(user: user, role: role).save()
        SpringSecurityUtils.clientRegisterFilter('statelessAuthenticationFilter',
                SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)
    }
    def destroy = {
    }
}
