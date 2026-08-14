package com.ipwho;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IpGeoResponse {
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
    public static class GeoData {
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
    public static class GeoLocation {
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
    public static class Timezone {
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
    public static class Flag {
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
    public static class Currency {
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
    public static class Connection {
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
    public static class Security {
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
    public static class UserAgent {
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
    public static class Browser {
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
    public static class Engine {
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
    public static class OS {
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
    public static class Device {
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
    public static class CPU {
        @JsonProperty("architecture")
        private String architecture;

        public String getArchitecture() { return architecture; }
        public void setArchitecture(String v) { this.architecture = v; }
    }
}
