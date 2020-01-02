package com.example.service;

import com.example.store.JWTBody;
import com.example.store.JWTHeader;
import com.google.gson.Gson;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTService {
    static Gson gson = new Gson();

    public String generateToken() {
        try {
            String encodedHeader = base64URLEncode( getHeader().getBytes(StandardCharsets.UTF_8) );
            String encodedClaims = base64URLEncode( getBody().getBytes(StandardCharsets.UTF_8) );

            String concatenated = encodedHeader + '.' + encodedClaims;

            byte[] signature = hmacSha256( concatenated, "mysecret" );

            String token = concatenated + '.' + base64URLEncode( signature );

            return token;
        } catch (Exception e) {
            System.out.println("GenerateToken Error");

            return "Error";
        }
    }

    private byte[] hmacSha256(String data, String secret) throws Exception {
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
        sha256Hmac.init(secretKey);

        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return signedBytes;
    }

    private String base64URLEncode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }


    private String getHeader() {
        JWTHeader header = new JWTHeader();

        return gson.toJson(header);
    }

    private String getBody() {
        JWTBody body = new JWTBody("test");

        return gson.toJson(body);
    }
}
