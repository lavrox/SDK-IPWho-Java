package com.ipwho;

public class NotFoundException extends IpWhoException {
    public NotFoundException(int statusCode, String message) {
        super(statusCode, message);
    }
}
