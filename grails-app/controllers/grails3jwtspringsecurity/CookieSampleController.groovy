package grails3jwtspringsecurity

import grails.plugin.springsecurity.annotation.Secured

@Secured('permitAll')
class CookieSampleController {
    def cookieService

    def index() {
        cookieService.setCookie('SampleCookie', "Hithere")

        render cookieService.find('SampleCookie')
    }

    def delete() {
        cookieService.deleteCookie('SampleCookie')
        render 'Cookie Deleted'
    }
}
