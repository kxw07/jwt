package com.example.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JWTService {
    static Gson gson = new Gson();
    private String secret = "mysecret";

    public String generateToken() {
        try {
            String base64urlHeader = encodeBase64URL( getHeader().getBytes(StandardCharsets.UTF_8) );
            String base64urlPayload = encodeBase64URL( getPayload().getBytes(StandardCharsets.UTF_8) );

            String concatenated = base64urlHeader + '.' + base64urlPayload;

            byte[] signature = encodeHmacSha256( concatenated, secret );

            String token = concatenated + '.' + encodeBase64URL( signature );

            return token;
        } catch (Exception e) {
            System.out.println("GenerateToken Error");

            return "Error";
        }
    }

    public boolean verifyToken (String token) throws Exception {
        String[] stringArray = token.split("\\.");

        String base64urlHeader = stringArray[0];
        String base64urlPayload = stringArray[1];
        String base64urlSignature = stringArray[2];

        String concatenated = base64urlHeader + '.' + base64urlPayload;

        byte[] calculatedSignature = encodeHmacSha256( concatenated, secret );

        return base64urlSignature.matches(encodeBase64URL( calculatedSignature ));
    }

    private byte[] encodeHmacSha256(String data, String secret) throws Exception {
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
        sha256Hmac.init(secretKey);

        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return signedBytes;
    }

    private String getHeader() {
        Map<String, String> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        return gson.toJson(header);
    }

    private String getPayload() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", "test");
        payload.put("name", "Kai");
        payload.put("iat", Instant.now().getEpochSecond());

        return gson.toJson(payload);
    }

    private String encodeBase64URL(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private byte[] decodeBase64URL(String str) {
        return Base64.getUrlDecoder().decode(str);
    }
}
