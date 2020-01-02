package com.example.store;

import java.util.HashMap;
import java.util.Map;

public class JWTHeader {
    private final String alg = "HS256";

    protected Map<String, String> publisher = new HashMap<>();

    public JWTHeader(){
        publisher.put("alg", alg);
    }
}