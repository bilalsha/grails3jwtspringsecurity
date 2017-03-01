package grails3jwtspringsecurity

class HomeController {

    def springSecurityService

    def index() {
        render "hello ${springSecurityService.currentUser.username}"
    }
}
