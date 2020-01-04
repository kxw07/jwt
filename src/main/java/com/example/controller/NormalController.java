package com.example.controller;

import com.example.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormalController {

    private JWTService jwtService;

    NormalController(@Autowired JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @RequestMapping(value = "/")
    public String greeting() {
        return "Hello!!!";
    }

    @RequestMapping(value = "/testjwt")
    public String testjwt(@RequestHeader("token") String token) {
        try {
            if (jwtService.verifyToken(token)) {
                return "Your Token is True!";
            } else {
                return "Your Token is Fake!!!";
            }
        } catch (Exception e) {
            return "Token Verify Fail.";
        }
    }
}
