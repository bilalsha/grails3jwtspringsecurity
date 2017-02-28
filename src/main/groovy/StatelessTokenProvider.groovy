import javax.servlet.http.HttpServletResponse

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
interface StatelessTokenProvider {
    void init(Integer expirationTime)
    String generateToken(HttpServletResponse httpServletResponse, String userName, String salt, Map<String,String> extraData)
    Map validateAndExtractToken(String token)
}