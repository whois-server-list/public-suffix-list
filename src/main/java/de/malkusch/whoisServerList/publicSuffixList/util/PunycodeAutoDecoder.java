package de.malkusch.whoisServerList.publicSuffixList.util;

import java.net.IDN;

import net.jcip.annotations.NotThreadSafe;

/**
 * Automatic Punycode Codec.
 *
 * This codec remembers at {@link PunycodeAutoDecoder#decode(String)}
 * whether the input was encoded or not.
 * The {@link PunycodeAutoDecoder#recode(String)} method will return
 * the same format as the original input was.
 *
 * This object has a state.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@NotThreadSafe
public final class PunycodeAutoDecoder {

    /**
     * Saved state of {@code PunycodeAutoDecoder#decode(String)}.
     */
    private boolean decoded;

    /**
     * Decodes a domain name into UTF-8 if it is in Punycode ASCII.
     *
     * If the domain name was already UTF-8 nothing will happen. This
     * method saves the original format (Punycode or UTF-8) in
     * {@link #decoded}. {@link #recode(String)} can return the string
     * in the saved format.
     *
     * @param domain  the domain name, may be null
     * @return the UTF-8 domain name
     */
    public String decode(final String domain) {
        if (domain == null) {
            return null;

        }
        String asciiDomain = IDN.toUnicode(domain);
        decoded = !asciiDomain.equals(domain);
        return asciiDomain;
    }

    /**
     * Returns the UTF-8 domain name in the original format.
     *
     * The original format is whether Punycode ASCII or UTF-8. The format
     * is determined in {@link #decode(String)}.
     *
     * @param domain  the UTF-8 domain name, not null
     * @return the domain name in the original format, not null
     */
    public String recode(final String domain) {
        return decoded ? IDN.toASCII(domain) : domain;
    }

    /**
     * Whether the original format was Punnycode ASCII.
     *
     * The original format is determined in {@link #decode(String)}.
     *
     * @return  true if the original format was Punnycode ASCII
     */
    public boolean isConverted() {
        return decoded;
    }

}
