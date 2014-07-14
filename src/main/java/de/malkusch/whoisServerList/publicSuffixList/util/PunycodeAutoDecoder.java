package de.malkusch.whoisServerList.publicSuffixList.util;

import java.net.IDN;

public class PunycodeAutoDecoder {

    private boolean decoded;

    public String decode(final String domain) {
        if (domain == null) {
            return null;

        }
        String asciiDomain = IDN.toUnicode(domain);
        decoded = !asciiDomain.equals(domain);
        return asciiDomain;
    }

    public String recode(final String domain) {
        return decoded ? IDN.toASCII(domain) : domain;
    }

    public boolean isConverted() {
        return decoded;
    }

}
