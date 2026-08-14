/*
 * IPWho Java SDK v1.0.0
 *
 * Enterprise-grade client for the IPWho IP Geolocation API.
 * API docs: https://api.ipwho.org
 *
 * Dependencies (Maven):
 *   - com.squareup.okhttp3:okhttp:4.12.0
 *   - com.fasterxml.jackson.core:jackson-databind:2.17.0
 *
 * Usage:
 *   IpWhoClient client = new IpWhoClient("sk.xxxx");
 *   IpGeoResponse resp = client.lookup("8.8.8.8");
 *   System.out.println(resp.getData().getGeoLocation().getCity());
 */

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

// ═══════════════════════════════════════════════════════════════════════════
// Exceptions
// ═══════════════════════════════════════════════════════════════════════════

/**
 * Base exception for IPWho API errors.
 */
class IpWhoException extends RuntimeException {
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

/**
 * Thrown when the API returns 404 (IP not found).
 */
class NotFoundException extends IpWhoException {
    public NotFoundException(int statusCode, String message) {
        super(statusCode, message);
    }
}

/**
 * Thrown when the API returns 429 (rate limit exceeded).
 */
class RateLimitException extends IpWhoException {
    public RateLimitException(int statusCode, String message) {
        super(statusCode, message);
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// Domain models — mirrors OpenAPI components/schemas exactly.
// ═══════════════════════════════════════════════════════════════════════════

/**
 * Top-level API response wrapper.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class IpGeoResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private GeoData data;

    @JsonProperty("message")
    private String message;

    // ── Getters ──────────────────────────────────────────────────────

    public boolean isSuccess() { return success; }
    public GeoData getData() { return data; }
    public String getMessage() { return message; }

    // ── Setters (Jackson) ────────────────────────────────────────────

    public void setSuccess(boolean success) { this.success = success; }
    public void setData(GeoData data) { this.data = data; }
    public void setMessage(String message) { this.message = message; }

    // ═══════════════════════════════════════════════════════════════════
    // Nested model classes
    // ═══════════════════════════════════════════════════════════════════

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class GeoData {
        @JsonProperty("ip")
        private String ip;

        @JsonProperty("geoLocation")
        private GeoLocation geoLocation;

        @JsonProperty("timezone")
        private Timezone timezone;

        @JsonProperty("flag")
        private Flag flag;

        @JsonProperty("currency")
        private Currency currency;

        @JsonProperty("connection")
        private Connection connection;

        @JsonProperty("security")
        private Security security;

        @JsonProperty("userAgent")
        private UserAgent userAgent;

        public String getIp() { return ip; }
        public GeoLocation getGeoLocation() { return geoLocation; }
        public Timezone getTimezone() { return timezone; }
        public Flag getFlag() { return flag; }
        public Currency getCurrency() { return currency; }
        public Connection getConnection() { return connection; }
        public Security getSecurity() { return security; }
        public UserAgent getUserAgent() { return userAgent; }

        public void setIp(String ip) { this.ip = ip; }
        public void setGeoLocation(GeoLocation geoLocation) { this.geoLocation = geoLocation; }
        public void setTimezone(Timezone timezone) { this.timezone = timezone; }
        public void setFlag(Flag flag) { this.flag = flag; }
        public void setCurrency(Currency currency) { this.currency = currency; }
        public void setConnection(Connection connection) { this.connection = connection; }
        public void setSecurity(Security security) { this.security = security; }
        public void setUserAgent(UserAgent userAgent) { this.userAgent = userAgent; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class GeoLocation {
        @JsonProperty("continent")
        private String continent;
        @JsonProperty("continentCode")
        private String continentCode;
        @JsonProperty("country")
        private String country;
        @JsonProperty("countryCode")
        private String countryCode;
        @JsonProperty("capital")
        private String capital;
        @JsonProperty("region")
        private String region;
        @JsonProperty("regionCode")
        private String regionCode;
        @JsonProperty("city")
        private String city;
        @JsonProperty("postal_Code")
        private String postalCode;
        @JsonProperty("dial_code")
        private String dialCode;
        @JsonProperty("is_in_eu")
        private Boolean isInEu;
        @JsonProperty("latitude")
        private Double latitude;
        @JsonProperty("longitude")
        private Double longitude;
        @JsonProperty("accuracy_radius")
        private Double accuracyRadius;

        public String getContinent() { return continent; }
        public String getContinentCode() { return continentCode; }
        public String getCountry() { return country; }
        public String getCountryCode() { return countryCode; }
        public String getCapital() { return capital; }
        public String getRegion() { return region; }
        public String getRegionCode() { return regionCode; }
        public String getCity() { return city; }
        public String getPostalCode() { return postalCode; }
        public String getDialCode() { return dialCode; }
        public Boolean getIsInEu() { return isInEu; }
        public Double getLatitude() { return latitude; }
        public Double getLongitude() { return longitude; }
        public Double getAccuracyRadius() { return accuracyRadius; }

        public void setContinent(String v) { this.continent = v; }
        public void setContinentCode(String v) { this.continentCode = v; }
        public void setCountry(String v) { this.country = v; }
        public void setCountryCode(String v) { this.countryCode = v; }
        public void setCapital(String v) { this.capital = v; }
        public void setRegion(String v) { this.region = v; }
        public void setRegionCode(String v) { this.regionCode = v; }
        public void setCity(String v) { this.city = v; }
        public void setPostalCode(String v) { this.postalCode = v; }
        public void setDialCode(String v) { this.dialCode = v; }
        public void setIsInEu(Boolean v) { this.isInEu = v; }
        public void setLatitude(Double v) { this.latitude = v; }
        public void setLongitude(Double v) { this.longitude = v; }
        public void setAccuracyRadius(Double v) { this.accuracyRadius = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Timezone {
        @JsonProperty("time_zone")
        private String timeZone;
        @JsonProperty("abbr")
        private String abbr;
        @JsonProperty("offset")
        private Double offset;
        @JsonProperty("is_dst")
        private Boolean isDst;
        @JsonProperty("utc")
        private String utc;
        @JsonProperty("current_time")
        private String currentTime;

        public String getTimeZone() { return timeZone; }
        public String getAbbr() { return abbr; }
        public Double getOffset() { return offset; }
        public Boolean getIsDst() { return isDst; }
        public String getUtc() { return utc; }
        public String getCurrentTime() { return currentTime; }

        public void setTimeZone(String v) { this.timeZone = v; }
        public void setAbbr(String v) { this.abbr = v; }
        public void setOffset(Double v) { this.offset = v; }
        public void setIsDst(Boolean v) { this.isDst = v; }
        public void setUtc(String v) { this.utc = v; }
        public void setCurrentTime(String v) { this.currentTime = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Flag {
        @JsonProperty("flag_Icon")
        private String flagIcon;
        @JsonProperty("flag_unicode")
        private String flagUnicode;

        public String getFlagIcon() { return flagIcon; }
        public String getFlagUnicode() { return flagUnicode; }

        public void setFlagIcon(String v) { this.flagIcon = v; }
        public void setFlagUnicode(String v) { this.flagUnicode = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Currency {
        @JsonProperty("code")
        private String code;
        @JsonProperty("symbol")
        private String symbol;
        @JsonProperty("name")
        private String name;
        @JsonProperty("name_plural")
        private String namePlural;
        @JsonProperty("hex_unicode")
        private String hexUnicode;

        public String getCode() { return code; }
        public String getSymbol() { return symbol; }
        public String getName() { return name; }
        public String getNamePlural() { return namePlural; }
        public String getHexUnicode() { return hexUnicode; }

        public void setCode(String v) { this.code = v; }
        public void setSymbol(String v) { this.symbol = v; }
        public void setName(String v) { this.name = v; }
        public void setNamePlural(String v) { this.namePlural = v; }
        public void setHexUnicode(String v) { this.hexUnicode = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Connection {
        @JsonProperty("asn_number")
        private Double asnNumber;
        @JsonProperty("asn_org")
        private String asnOrg;
        @JsonProperty("isp")
        private String isp;
        @JsonProperty("org")
        private String org;
        @JsonProperty("domain")
        private String domain;
        @JsonProperty("connection_type")
        private String connectionType;

        public Double getAsnNumber() { return asnNumber; }
        public String getAsnOrg() { return asnOrg; }
        public String getIsp() { return isp; }
        public String getOrg() { return org; }
        public String getDomain() { return domain; }
        public String getConnectionType() { return connectionType; }

        public void setAsnNumber(Double v) { this.asnNumber = v; }
        public void setAsnOrg(String v) { this.asnOrg = v; }
        public void setIsp(String v) { this.isp = v; }
        public void setOrg(String v) { this.org = v; }
        public void setDomain(String v) { this.domain = v; }
        public void setConnectionType(String v) { this.connectionType = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Security {
        @JsonProperty("isVpn")
        private Boolean isVpn;
        @JsonProperty("isTor")
        private Boolean isTor;
        @JsonProperty("isThreat")
        private String isThreat;  // "low" | "medium" | "high"

        public Boolean getIsVpn() { return isVpn; }
        public Boolean getIsTor() { return isTor; }
        public String getIsThreat() { return isThreat; }

        public void setIsVpn(Boolean v) { this.isVpn = v; }
        public void setIsTor(Boolean v) { this.isTor = v; }
        public void setIsThreat(String v) { this.isThreat = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class UserAgent {
        @JsonProperty("browser")
        private Browser browser;
        @JsonProperty("engine")
        private Engine engine;
        @JsonProperty("os")
        private OS os;
        @JsonProperty("device")
        private Device device;
        @JsonProperty("cpu")
        private CPU cpu;

        public Browser getBrowser() { return browser; }
        public Engine getEngine() { return engine; }
        public OS getOs() { return os; }
        public Device getDevice() { return device; }
        public CPU getCpu() { return cpu; }

        public void setBrowser(Browser v) { this.browser = v; }
        public void setEngine(Engine v) { this.engine = v; }
        public void setOs(OS v) { this.os = v; }
        public void setDevice(Device v) { this.device = v; }
        public void setCpu(CPU v) { this.cpu = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Browser {
        @JsonProperty("name")
        private String name;
        @JsonProperty("version")
        private String version;

        public String getName() { return name; }
        public String getVersion() { return version; }
        public void setName(String v) { this.name = v; }
        public void setVersion(String v) { this.version = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Engine {
        @JsonProperty("name")
        private String name;
        @JsonProperty("version")
        private String version;

        public String getName() { return name; }
        public String getVersion() { return version; }
        public void setName(String v) { this.name = v; }
        public void setVersion(String v) { this.version = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class OS {
        @JsonProperty("name")
        private String name;
        @JsonProperty("version")
        private String version;

        public String getName() { return name; }
        public String getVersion() { return version; }
        public void setName(String v) { this.name = v; }
        public void setVersion(String v) { this.version = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Device {
        @JsonProperty("type")
        private String type;
        @JsonProperty("vendor")
        private String vendor;
        @JsonProperty("model")
        private String model;

        public String getType() { return type; }
        public String getVendor() { return vendor; }
        public String getModel() { return model; }
        public void setType(String v) { this.type = v; }
        public void setVendor(String v) { this.vendor = v; }
        public void setModel(String v) { this.model = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class CPU {
        @JsonProperty("architecture")
        private String architecture;

        public String getArchitecture() { return architecture; }
        public void setArchitecture(String v) { this.architecture = v; }
    }
}

/**
 * Wrapper for bulk /bulk/ endpoint response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class BulkResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private BulkData data;

    public boolean isSuccess() { return success; }
    public BulkData getData() { return data; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setData(BulkData data) { this.data = data; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class BulkData {
        @JsonProperty("responseArray")
        private List<IpGeoResponse> responseArray;

        public List<IpGeoResponse> getResponseArray() { return responseArray; }
        public void setResponseArray(List<IpGeoResponse> responseArray) { this.responseArray = responseArray; }
    }
}

/**
 * Error payload returned on non-200 responses.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class ErrorResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
}

// ═══════════════════════════════════════════════════════════════════════════
// Client
// ═══════════════════════════════════════════════════════════════════════════

/**
 * Client for the IPWho API.
 *
 * Usage:
 * <pre>{@code
 * IpWhoClient client = new IpWhoClient("sk.xxxx");
 * IpGeoResponse resp = client.lookup("8.8.8.8");
 * System.out.println(resp.getData().getGeoLocation().getCity());
 * }</pre>
 */
class IpWhoClient {
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
