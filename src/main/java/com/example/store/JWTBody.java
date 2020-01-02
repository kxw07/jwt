package com.example.store;

import java.util.HashMap;
import java.util.Map;

public class JWTBody {
    protected Map<String, String> publisher = new HashMap<>();

    public JWTBody(String sub) {
        publisher.put("sub", sub);
    }
}