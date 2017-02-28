import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
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
class StateLessAuthenticationFilter extends GenericFilterBean{
    String filterProcessesUrl = 'j_spring_security_math_check'
    private TokenAuthenticationService authenticationService;

    @Override
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest
        HttpServletResponse response = (HttpServletResponse) servletResponse

        // If the request URI doesn't contain the filterProcessesUrl,
        // it isn't a request that should be handled by this filter
        if(!request.getRequestURI().contains(filterProcessesUrl)) {
            filterChain.doFilter(request, response)
            return
        }

        Authentication authentication = authenticationService.getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    TokenAuthenticationService getAuthenticationService() {
        return authenticationService
    }

    void setAuthenticationService(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService
    }
}
