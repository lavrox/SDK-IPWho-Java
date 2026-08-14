package com.ipwho;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
}
