package com.ipwho;

public class IpWhoException extends RuntimeException {
    private final int statusCode;

    public IpWhoException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public IpWhoException(String message) {
        this(0, message);
    }

    public IpWhoException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0;
    }

    public IpWhoException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
