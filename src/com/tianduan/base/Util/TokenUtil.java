package com.tianduan.base.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 生成和验证token
 */
public class TokenUtil {

    private static final String SECRET = "tianduan";

//    public static String baseGetToken(Date iatDate, Date expiresDate, String phone, String imei, String mac) {
//        Map<String, Object> headers = new HashMap<String, Object>();
//        headers.put("alg", "HS256");
//        headers.put("typ", "JWT");
//        try {
//            String token = JWT.create()
//                    .withHeader(headers)
//                    .withIssuer("hef")
//                    .withIssuedAt(iatDate)
//                    .withExpiresAt(expiresDate)
//                    .withClaim("phone", phone)
//                    .withClaim("imei", imei)
//                    .withClaim("mac", mac)
//                    .sign(Algorithm.HMAC256(SECRET));//加密
//            return token;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public static String getToken(String phone, String imei, String mac) {
//        //签发时间
//        Date iatDate = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 24*60);
//        Date expiresDate = calendar.getTime();//过期时间
//        Map<String, Object> headers = new HashMap<String, Object>();
//        return baseGetToken(iatDate, expiresDate, phone, imei, mac);
//    }

    public static String baseGetToken(Date iatDate, Date expiresDate, String phone, String objectId) {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        try {
            String token = JWT.create()
                    .withHeader(headers)
                    .withIssuer("hef")
                    .withIssuedAt(iatDate)
                    .withExpiresAt(expiresDate)
                    .withClaim("phone", phone)
                    .withClaim("objectId", objectId)
                    .sign(Algorithm.HMAC256(SECRET));//加密
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getToken(String phone, String objectId) {
        //签发时间
        Date iatDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 24*60);
        Date expiresDate = calendar.getTime();//过期时间
        Map<String, Object> headers = new HashMap<String, Object>();
        return baseGetToken(iatDate, expiresDate, phone, objectId);
    }

    public static Map<String, String> verifyToken(String token) {
        Map<String, String> result = new HashMap<>();
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("hef")
                    .build();
            try {
                DecodedJWT jwt = verifier.verify(token);
                Map<String, Claim> claimMap = jwt.getClaims();
                Set<String> keys = claimMap.keySet();
                for (String key : keys) {
                    result.put(key, claimMap.get(key).asString());
                }
                return result;
            } catch (TokenExpiredException e) {
                e.printStackTrace();
                result.put("expires", "凭证已过期！");
                return result;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        result.put("err", "凭证不匹配！");
        return result;
    }


//    public static void main(String[] args) {
//        String token = getToken("15802918993", "123456789012", "34:12:23:67:78:89");
//        System.out.println(token);
//        System.out.println(verifyToken(token));
//    }

}
