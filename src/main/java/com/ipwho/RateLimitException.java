package com.ipwho;

public class RateLimitException extends IpWhoException {
    public RateLimitException(int statusCode, String message) {
        super(statusCode, message);
    }
}
