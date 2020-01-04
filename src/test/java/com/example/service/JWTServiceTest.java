package com.example.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JWTServiceTest {
    private JWTService jWTService;

    @Before
    public void setUp() {
        jWTService = new JWTService();
    }

    @Test
    public void when_generateToken_then_return_token() {
        Assert.assertNotNull(jWTService.generateToken());
    }

    @Test
    public void given_real_token_when_verifyToken_then_return_true() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0IiwibmFtZSI6IkthaSIsImlhdCI6MTU3Nzk0NjUzM30.6KZ7ChkA6CxjDt3pHtdrLkkyPaX-hmgoWtXj060gU40";

        Assert.assertTrue(jWTService.verifyToken(token));
    }

    @Test
    public void given_fake_token_when_verifyToken_then_return_false() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        Assert.assertFalse(jWTService.verifyToken(token));
    }
}