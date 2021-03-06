import exception.StatelessValidationException
import grails3jwtspringsecurity.CryptoService
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat

import javax.servlet.http.HttpServletResponse

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
@Slf4j
class JwtStatelessTokenProvider implements StatelessTokenProvider{
    private static final String BEARER = "Bearer "

    CryptoService cryptoService
    Integer expirationTime

    @Override
    void init(Integer expirationTime) {
        this.expirationTime = expirationTime
    }

    @Override
    String generateToken(HttpServletResponse httpServletResponse, String userName, String salt, Map<String, String> extraData) {
        def data = [username:userName]

        if (extraData) {
            data["extradata"] = extraData
        }

        if (salt != null) {
            data["salt"] = salt
        }

        DateTimeFormatter formatter = ISODateTimeFormat.dateTime()
        data["issued_at"] = formatter.print(new DateTime())

        if (expirationTime != null) {
            data["expires_at"] = formatter.print(new DateTime().plusMinutes(expirationTime))
        }

        String header = new JsonBuilder([alg:"HS256", typ: "JWT"])
        String payload = new JsonBuilder(data).toString()
        String signature = cryptoService.hash("${UrlSafeBase64Utils.encode(header.bytes)}.${UrlSafeBase64Utils.encode(payload.bytes)}")

        return "${UrlSafeBase64Utils.encode(header.bytes)}.${UrlSafeBase64Utils.encode(payload.bytes)}.${signature}"
    }

    @Override
    Map validateAndExtractToken(String token) {
        if (!token) {
            log.debug "Token must be present"
            throw new StatelessValidationException("Invalid token")
        }

        if (token.startsWith(BEARER)){
            token = token.substring(BEARER.size())
        }

        def (header64, payload64, signature) = token.tokenize(".")

        if (header64 == null || payload64 == null || signature == null) {
            log.debug "Token should have two points to split"
            throw new StatelessValidationException("Invalid token")
        }

        // Validate signature
        String expectedSignature = cryptoService.hash("${header64}.${payload64}")

        if (signature != expectedSignature) {
            throw new StatelessValidationException("Invalid token")
        }

        // Extract the payload
        def slurper = new JsonSlurper()
        def payload = new String(UrlSafeBase64Utils.decode(payload64))

        return (Map)slurper.parseText(payload)
    }
}
