package com.example.service;

import com.google.gson.Gson;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JWTService {
    static Gson gson = new Gson();

    public String generateToken() {
        try {
            String encodedHeader = encode( getHeader().getBytes(StandardCharsets.UTF_8) );
            String encodedClaims = encode( getBody().getBytes(StandardCharsets.UTF_8) );

            String concatenated = encodedHeader + '.' + encodedClaims;

            byte[] signature = encodeHmacSha256( concatenated, "mysecret" );

            String token = concatenated + '.' + encode( signature );

            return token;
        } catch (Exception e) {
            System.out.println("GenerateToken Error");

            return "Error";
        }
    }

    public Map decodeToken(String token) throws Exception {
        String[] stringArray = token.split("\\.");

        String header = stringArray[0];
        String body = stringArray[1];
        String signature = stringArray[2];

        header = new String(decode(header));
        body = new String(decode(body));
        signature = new String(decodeHmacSha256(decode(signature), "mysecret"));

        Map<String, Object> decodeToken = new HashMap<>();
        decodeToken.put("header", header);
        decodeToken.put("body", body);
        decodeToken.put("signature", signature);

        return decodeToken;
    }

    private byte[] encodeHmacSha256(String data, String secret) throws Exception {
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
        sha256Hmac.init(secretKey);

        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return signedBytes;
    }

    private byte[] decodeHmacSha256(byte[] data, String secret) throws Exception {
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
        sha256Hmac.init(secretKey);
        
        byte[] signedBytes = sha256Hmac.doFinal(data);

        return signedBytes;
    }

    private String getHeader() {
        Map<String, String> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        return gson.toJson(header);
    }

    private String getBody() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("sub", "test");
        body.put("name", "Kai");
        body.put("iat", Instant.now().getEpochSecond());

        return gson.toJson(body);
    }

    private String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private byte[] decode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }
}
