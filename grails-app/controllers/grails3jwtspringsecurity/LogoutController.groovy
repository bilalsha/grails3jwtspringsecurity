package grails3jwtspringsecurity

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

import javax.servlet.http.Cookie

@Secured('permitAll')
class LogoutController {

    def cookieService
    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    def index = {
        // TODO put any pre-logout code here
        Cookie cookie = cookieService.findCookie('access-token')
        cookieService.deleteCookie(cookie)
        redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
    }
}
