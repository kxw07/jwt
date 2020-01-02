package com.example.service;

import org.junit.jupiter.api.Test;

import java.util.Map;

class JWTServiceTest {
    @Test
    public void testGenerateToken() {
        JWTService jWTService = new JWTService();
        System.out.println(jWTService.generateToken());
    }

    @Test
    public void testDecode() throws Exception {
        JWTService jWTService = new JWTService();
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0IiwibmFtZSI6IkthaSIsImlhdCI6MTU3Nzk0NjUzM30.6KZ7ChkA6CxjDt3pHtdrLkkyPaX-hmgoWtXj060gU40";
        Map map = jWTService.decodeToken(token);

        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}