package com.ipwho;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.HttpUrl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class IpWhoClient {
    private static final String DEFAULT_BASE_URL = "https://api.ipwho.org";
    private static final String VERSION = "1.0.0";

    private final String apiKey;
    private final String baseUrl;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Creates a new IPWho API client.
     *
     * @param apiKey Your IPWho API key (required).
     */
    public IpWhoClient(String apiKey) {
        this(apiKey, DEFAULT_BASE_URL, new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build());
    }

    /**
     * Creates a new IPWho API client with custom base URL and HTTP client.
     *
     * @param apiKey     Your IPWho API key.
     * @param baseUrl    Override the API base URL.
     * @param httpClient Custom OkHttpClient instance.
     */
    public IpWhoClient(String apiKey, String baseUrl, OkHttpClient httpClient) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("apiKey is required");
        }
        this.apiKey = apiKey;
        this.baseUrl = baseUrl.replaceAll("/$", "");
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // ── Public API ──────────────────────────────────────────────────

    /**
     * Look up geolocation for a specific IP address.
     *
     * @param ip     The IPv4 or IPv6 address.
     * @param format Response format: "json" (default), "xml", "csv".
     * @param fields Comma-separated list of fields to include, or null for all.
     * @return IpGeoResponse with the lookup result.
     * @throws IpWhoException on API or transport errors.
     */
    public IpGeoResponse lookup(String ip, String format, String fields) {
        HttpUrl url = buildUrl("/ip/" + ip, format, fields);
        return execute(url);
    }

    /**
     * Look up geolocation for a specific IP address (JSON, all fields).
     */
    public IpGeoResponse lookup(String ip) {
        return lookup(ip, null, null);
    }

    /**
     * Look up geolocation for the caller's own IP address.
     *
     * @param format Response format (see {@link #lookup(String, String, String)}).
     * @param fields Fields filter.
     * @return IpGeoResponse with the caller's geolocation.
     */
    public IpGeoResponse me(String format, String fields) {
        HttpUrl url = buildUrl("/me", format, fields);
        return execute(url);
    }

    /**
     * Look up geolocation for the caller's own IP address (JSON, all fields).
     */
    public IpGeoResponse me() {
        return me(null, null);
    }

    /**
     * Perform geolocation lookups for multiple IP addresses.
     *
     * @param ips List of IPv4 or IPv6 addresses.
     * @return List of IpGeoResponse, one per IP in the same order.
     * @throws IpWhoException on API or transport errors.
     */
    public List<IpGeoResponse> bulk(List<String> ips) {
        if (ips == null || ips.isEmpty()) {
            throw new IllegalArgumentException("ips must not be empty");
        }
        String bulkParam = String.join(",", ips);
        HttpUrl url = buildUrl("/bulk/" + bulkParam, null, null);
        return executeBulk(url);
    }

    // ── Internal ────────────────────────────────────────────────────

    private HttpUrl buildUrl(String path, String format, String fields) {
        HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(baseUrl + path))
                .newBuilder()
                .addQueryParameter("apiKey", apiKey);
        if (format != null && !format.isEmpty() && !"json".equals(format)) {
            builder.addQueryParameter("format", format);
        }
        if (fields != null && !fields.isEmpty()) {
            builder.addQueryParameter("get", fields);
        }
        return builder.build();
    }

    private IpGeoResponse execute(HttpUrl url) {
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("User-Agent", "ipwho-java-sdk/" + VERSION)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            int statusCode = response.code();
            String body = response.body() != null ? response.body().string() : "";

            if (statusCode != 200) {
                throw mapError(statusCode, body);
            }

            // Non-JSON format
            String format = url.queryParameter("format");
            if (format != null && !"json".equals(format)) {
                IpGeoResponse r = new IpGeoResponse();
                r.setSuccess(true);
                IpGeoResponse.GeoData data = new IpGeoResponse.GeoData();
                data.setIp(body);
                r.setData(data);
                return r;
            }

            IpGeoResponse resp = objectMapper.readValue(body, IpGeoResponse.class);
            if (!resp.isSuccess()) {
                throw new IpWhoException(statusCode,
                        resp.getMessage() != null ? resp.getMessage() : "API returned success=false");
            }
            return resp;
        } catch (IpWhoException e) {
            throw e;
        } catch (IOException e) {
            throw new IpWhoException("Request failed: " + e.getMessage(), e);
        }
    }

    private List<IpGeoResponse> executeBulk(HttpUrl url) {
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("User-Agent", "ipwho-java-sdk/" + VERSION)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            int statusCode = response.code();
            String body = response.body() != null ? response.body().string() : "";

            if (statusCode != 200) {
                throw mapError(statusCode, body);
            }

            BulkResponse bulkResp = objectMapper.readValue(body, BulkResponse.class);
            if (!bulkResp.isSuccess()) {
                throw new IpWhoException(statusCode, "API returned success=false for bulk request");
            }
            if (bulkResp.getData() == null || bulkResp.getData().getResponseArray() == null) {
                throw new IpWhoException(statusCode, "Bulk response data is null");
            }
            return bulkResp.getData().getResponseArray();
        } catch (IpWhoException e) {
            throw e;
        } catch (IOException e) {
            throw new IpWhoException("Request failed: " + e.getMessage(), e);
        }
    }

    private IpWhoException mapError(int statusCode, String body) {
        String message = "HTTP " + statusCode;
        try {
            ErrorResponse err = objectMapper.readValue(body, ErrorResponse.class);
            if (err.getMessage() != null && !err.getMessage().isEmpty()) {
                message = err.getMessage();
            }
        } catch (Exception ignored) {
            if (!body.isEmpty()) message = body;
        }
        switch (statusCode) {
            case 404: return new NotFoundException(statusCode, message);
            case 429: return new RateLimitException(statusCode, message);
            default:  return new IpWhoException(statusCode, message);
        }
    }
}
