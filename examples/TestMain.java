package com.ipwho;

import java.util.List;
import java.util.Arrays;

public class TestMain {
    static int pass = 0, fail = 0;
    static void ok(boolean c, String m) {
        if (c) { pass++; System.out.println("  PASS " + m); }
        else   { fail++; System.out.println("  FAIL " + m); }
    }

    public static void main(String[] args) throws Exception {
        String key = System.getenv("IPWHO_API_KEY");
        IpWhoClient c = new IpWhoClient(key);

        // 1. lookup
        IpGeoResponse r = c.lookup("8.8.8.8");
        IpGeoResponse.GeoData d = r.getData();
        var gl = d.getGeoLocation();
        var tz = d.getTimezone();
        var fl = d.getFlag();
        var cu = d.getCurrency();
        var cn = d.getConnection();
        ok("8.8.8.8".equals(d.getIp()), "lookup ip == 8.8.8.8");
        ok("United States".equals(gl.getCountry()), "country == United States (got " + gl.getCountry() + ")");
        ok(cn.getAsnNumber() != null && cn.getAsnNumber().intValue() == 15169, "asn_number == 15169 (got " + cn.getAsnNumber() + ")");
        ok(gl.getDialCode() != null, "dial_code captured (" + gl.getDialCode() + ")");
        ok(gl.getIsInEu() != null, "is_in_eu captured");
        ok(tz.getTimeZone() != null, "time_zone captured (" + tz.getTimeZone() + ")");
        ok(fl.getFlagIcon() != null, "flag_Icon captured (" + fl.getFlagIcon() + ")");
        ok(fl.getFlagUnicode() != null, "flag_unicode captured (" + fl.getFlagUnicode() + ")");
        ok(cu.getNamePlural() != null, "name_plural captured (" + cu.getNamePlural() + ")");
        ok(cn.getAsnOrg() != null, "asn_org captured (" + cn.getAsnOrg() + ")");
        ok(cn.getConnectionType() != null, "connection_type captured (" + cn.getConnectionType() + ")");

        // 2. me
        IpGeoResponse me = c.me();
        ok(me.getData() != null && me.getData().getIp() != null, "me ip captured (" + me.getData().getIp() + ")");

        // 3. bulk
        List<IpGeoResponse> b = c.bulk(Arrays.asList("8.8.8.8", "1.1.1.1"));
        ok(b != null && b.size() == 2, "bulk returns 2 (got " + (b == null ? "null" : b.size()) + ")");

        // 4. bad key
        try {
            new IpWhoClient("sk.invalid_test_key").lookup("8.8.8.8");
            ok(false, "bad key should raise");
        } catch (IpWhoException e) {
            ok(true, "bad key raised " + e.getClass().getSimpleName());
        } catch (Exception e) {
            ok(true, "bad key raised " + e.getClass().getSimpleName());
        }

        System.out.println("\nJAVA RESULT: " + pass + " passed, " + fail + " failed");
        System.exit(fail > 0 ? 1 : 0);
    }
}
