import exception.StatelessValidationException

/**
 * Copyright (c) 2017, AskLytics and/or its affiliates. All rights reserved.
 *
 * ASKLYTICS PROPRIETARY/CONFIDENTIAL. Use is subject to Non-Disclosure Agreement.
 *
 * Created by bilalshah on 28/02/2017
 *
 */
class UrlSafeBase64Utils {

    private static String replacePairs(String s, List charPairs) {
        charPairs.each { pair ->
            s = s.replaceAll(pair.first(), pair.last())
        }
        return s
    }

    static String encode(byte[] arg) {
        List charPairs = [['=', ''], ['\\+', '-'], ['/', '_']]
        String s = arg.encodeBase64()
        return replacePairs(s, charPairs)
    }

    static byte[] decode(String arg) {
        List charPairs = [['-', '+'], ['_', '/']]
        String s = replacePairs(arg, charPairs)
        switch(s.size() % 4) {
            case 0:
                break
            case 2:
                s += '=='
                break
            case 3:
                s += '='
                break
            default:
                throw new StatelessValidationException("Illegal base64url string")
        }

        return s.decodeBase64()
    }
}
