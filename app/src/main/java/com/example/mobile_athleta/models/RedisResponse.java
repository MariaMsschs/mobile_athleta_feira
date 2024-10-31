package com.example.mobile_athleta.models;

public class RedisResponse {
    String message;
    String key;

    public RedisResponse(String message, String key) {
        this.message = message;
        this.key = key;
    }

    public RedisResponse(String message) {
        this.message = message;
    }

    public RedisResponse(){}

    public String getMessage() {
        return message;
    }

    public String getKey() {
        return key;
    }
}
