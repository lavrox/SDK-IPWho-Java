package com.ipwho;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BulkResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private BulkData data;

    public boolean isSuccess() { return success; }
    public BulkData getData() { return data; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setData(BulkData data) { this.data = data; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BulkData {
        @JsonProperty("responseArray")
        private List<IpGeoResponse> responseArray;

        public List<IpGeoResponse> getResponseArray() { return responseArray; }
        public void setResponseArray(List<IpGeoResponse> responseArray) { this.responseArray = responseArray; }
    }
}
