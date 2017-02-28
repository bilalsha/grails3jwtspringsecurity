import grails3jwtspringsecurity.CryptoService

// Place your Spring DSL code here
beans = {

    statelessAuthenticationFilter(StateLessAuthenticationFilter){
        authenticationService = ref('authenticationService')
    }
    userDetailsService(MyUserDetailsService)
    tokenHandler(TokenHandler){
        secret = "ABCDEF"
        userService = ref('userDetailsService')
    }
    authenticationService(TokenAuthenticationService){
        secret = "ABCDEF"
        tokenHandler = ref('tokenHandler')
    }

    statelessTokenProvider(JwtStatelessTokenProvider){
        cryptoService = ref('cryptoService')
        expirationTime = 3600
    }

    cryptoService(CryptoService){
        secret = "ABCDEFGHIJKL"
    }
}
