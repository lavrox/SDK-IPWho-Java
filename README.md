# IPWho ([ipwho.org](https://www.ipwho.org)) Java SDK

[![Java version](https://img.shields.io/badge/java-11+-orange.svg)](https://www.java.com/) [![license](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/lavrox/SDK-IPWho-Java/blob/main/LICENSE)

Official Java client for the [IPWho](https://www.ipwho.org) IP Intelligence API. One call returns the **full** payload: geolocation, timezone, flag, currency, connection (ASN/ISP), security, and user-agent when present.

- Product: [ipwho.org](https://www.ipwho.org)
- API docs: [ipwho.org/docs](https://www.ipwho.org/docs)
- Get an API key: [ipwho.org](https://www.ipwho.org)
- Live API host: `https://api.ipwho.org`

## Installation

Maven:

```xml
<dependency>
    <groupId>org.ipwho</groupId>
    <artifactId>ipwho-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

Gradle: `implementation("org.ipwho:ipwho-sdk:1.0.0")`

Java 11+, OkHttp, Jackson. Package: `com.ipwho`.

## Quick Start

```java
IpWhoClient client = new IpWhoClient(System.getenv("IPWHO_API_KEY"));

IpGeoResponse resp = client.lookup("8.8.8.8");          // GET /ip/{ip}
IpGeoResponse me = client.me();                         // GET /me
List<IpGeoResponse> bulk = client.bulk(Arrays.asList("8.8.8.8", "1.1.1.1"));
```

```
IpGeoResponse
├── isSuccess()
├── getMessage()
└── getData()  GeoData
    ├── getIp()
    ├── getGeoLocation()
    ├── getTimezone()
    ├── getFlag()
    ├── getCurrency()
    ├── getConnection()
    ├── getSecurity()
    └── getUserAgent()
```

## Reading the full response (8.8.8.8)

Live [IPWho](https://www.ipwho.org) values: United States, ASN 15169, America/Chicago, dial code +1. Nested objects may be `null`.

```java
IpGeoResponse.GeoData data = client.lookup("8.8.8.8").getData();
System.out.println(data.getIp()); // 8.8.8.8

IpGeoResponse.GeoLocation geo = data.getGeoLocation();
System.out.println(geo.getCountry());      // United States
System.out.println(geo.getCountryCode());  // US
System.out.println(geo.getContinent());
System.out.println(geo.getContinentCode());
System.out.println(geo.getCapital());
System.out.println(geo.getRegion());
System.out.println(geo.getRegionCode());
System.out.println(geo.getCity());
System.out.println(geo.getPostalCode());
System.out.println(geo.getDialCode());     // +1
System.out.println(geo.getIsInEu());
System.out.println(geo.getLatitude());
System.out.println(geo.getLongitude());
System.out.println(geo.getAccuracyRadius()); // e.g. 1000

IpGeoResponse.Timezone tz = data.getTimezone();
System.out.println(tz.getTimeZone()); // America/Chicago
System.out.println(tz.getAbbr());
System.out.println(tz.getOffset());
System.out.println(tz.getIsDst());
System.out.println(tz.getUtc());
System.out.println(tz.getCurrentTime());

System.out.println(data.getFlag().getFlagIcon());    // 🇺🇸
System.out.println(data.getFlag().getFlagUnicode()); // U+1F1FA U+1F1F8

System.out.println(data.getCurrency().getCode());
System.out.println(data.getCurrency().getNamePlural()); // US dollars

IpGeoResponse.Connection conn = data.getConnection();
System.out.println(conn.getAsnNumber());      // 15169
System.out.println(conn.getAsnOrg());         // Google LLC
System.out.println(conn.getIsp());
System.out.println(conn.getOrg());
System.out.println(conn.getDomain());
System.out.println(conn.getConnectionType()); // Corporate

System.out.println(data.getSecurity().getIsVpn());
System.out.println(data.getSecurity().getIsTor());
System.out.println(data.getSecurity().getIsThreat());

if (data.getUserAgent() != null) {
    System.out.println(data.getUserAgent().getBrowser().getName());
}

System.out.println(client.me().getData().getIp());

for (IpGeoResponse item : client.bulk(Arrays.asList("8.8.8.8", "1.1.1.1"))) {
    System.out.println(item.getData().getIp());
}
```

Overloads: `lookup(ip, format, fields)`, `me(format, fields)` (`json`/`xml`/`csv`).

## API Reference

### `new IpWhoClient(String apiKey)`

Also `IpWhoClient(apiKey, baseUrl, okHttpClient)`. Empty key → `IllegalArgumentException`. Query `apiKey`.

### Errors

`NotFoundException` (404), `RateLimitException` (429), `IpWhoException`.

## Type Definitions

Getters on nested static classes of `IpGeoResponse`:

- **GeoLocation**: continent, continentCode, country, countryCode, capital, region, regionCode, city, postalCode, dialCode, isInEu, latitude, longitude, accuracyRadius
- **Timezone**: timeZone, abbr, offset, isDst, utc, currentTime
- **Flag**: flagIcon, flagUnicode
- **Currency**: code, symbol, name, namePlural, hexUnicode
- **Connection**: asnNumber, asnOrg, isp, org, domain, connectionType
- **Security**: isVpn, isTor, isThreat
- **UserAgent**: browser, engine, os, device, cpu

Jackson maps mixed wire keys (`postal_Code`, `flag_Icon`, `isVpn`).

## Troubleshooting

- Key: [ipwho.org](https://www.ipwho.org).
- HTTP 403: SDK sends `ipwho-java-sdk/1.0.0`.
- Null nested objects on some IPs.

## Testing

```bash
IPWHO_API_KEY=your_key java TestMain
```

The live check is `TestMain.java` (compile it together with the SDK sources and OkHttp/Jackson).

## Changelog

### v1.0.0

- `lookup`, `me`, `bulk` matching [api.ipwho.org](https://api.ipwho.org)

## License

MIT License — see [LICENSE](LICENSE).

## Support

- Documentation: [ipwho.org/docs](https://www.ipwho.org/docs)
- Contact: [ipwho.org/contact](https://www.ipwho.org/contact)
- GitHub Issues: [lavrox/SDK-IPWho-Java](https://github.com/lavrox/SDK-IPWho-Java/issues)
- Website: [ipwho.org](https://www.ipwho.org)

---

Product by [lavrox.com](https://lavrox.com)
