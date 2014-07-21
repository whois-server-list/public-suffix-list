package de.malkusch.whoisServerList.publicSuffixList;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.StringUtils;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;
import de.malkusch.whoisServerList.publicSuffixList.util.PunycodeAutoDecoder;

/**
 * API for the Public Suffix List.
 *
 * Use {@link PublicSuffixListFactory} for building this api.
 *
 * You can use the API's methods with UTF-8 domain names or Punycode
 * encoded ASCII domain names. The API will return the results in
 * the same format as the input was. I.e. if you use an UTF-8 string
 * the result will be an UTF-8 String as well. Same for Punycode.
 *
 * The API is case insensitive.
 *
 * @author markus@malkusch.de
 *
 * @see PublicSuffixListFactory
 * @see <a href="https://publicsuffix.org/">https://publicsuffix.org/</a>
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
public final class PublicSuffixList {

    /**
     * Public Suffix List url.
     */
    private final URL url;

    /**
     * Public Suffix List encoding.
     */
    private final Charset charset;

    /**
     * Rule index.
     */
    private final Index index;

    /**
     * Initializes the Public List API.
     *
     * @param index    the rule index
     * @param url      the Public Suffix List url
     * @param charset  the character encoding of the list
     *
     * @see PublicSuffixListFactory#build()
     */
    PublicSuffixList(final Index index, final URL url,
            final Charset charset) {

        this.index = index;
        this.url = url;
        this.charset = charset;
    }

    /**
     * Gets the registrable domain.
     *
     * E.g. "www.example.net" and "example.net" will return "example.net".
     * Null, an empty string or domains with a leading dot will return null.
     *
     * This method is case insensitive.
     *
     * @param domain  the domain name, null returns null
     * @return the registrable domain,
     *         null if the domain is not registrable at all
     */
    public String getRegistrableDomain(final String domain) {
        if (StringUtils.isEmpty(domain)) {
            return null;

        }
        /*
         * Mozilla's test cases implies that leading dots
         * result to no registrable domain.
         * @see ExampleTest
         */
        if (domain.charAt(0) == '.') {
            return null;

        }
        PunycodeAutoDecoder punycode = new PunycodeAutoDecoder();
        String decodedDomain = punycode.decode(domain);

        String suffix = getPublicSuffix(decodedDomain);
        if (StringUtils.equals(decodedDomain, suffix)) {
            return null;

        }
        String[] suffixLabels = DomainUtil.splitLabels(suffix);
        if (suffixLabels == null) {
            return null;

        }
        String[] labels = DomainUtil.splitLabels(decodedDomain);
        int offset = labels.length - suffixLabels.length - 1;
        String registrableDomain = DomainUtil.joinLabels(
                Arrays.copyOfRange(labels, offset, labels.length));

        return punycode.recode(registrableDomain);
    }

    /**
     * Returns whether a domain is registrable.
     *
     * E.g. example.net is registrable, "www.example.net" and "net" are not.
     *
     * This method is case insensitive.
     *
     * @param domain  the domain name, not null
     * @return {@code true} if the domain is registrable
     */
    public boolean isRegistrable(final String domain) {
        if (domain == null) {
            throw new IllegalArgumentException("The domain must not be null");

        }
        return domain.equals(getRegistrableDomain(domain));
    }

    /**
     * Returns the public suffix from a domain.
     *
     * If the domain is already a public suffix, it will be returned unchanged.
     * E.g. "www.example.net" will return "net".
     *
     * This method is case insensitive.
     *
     * @param domain  the domain name
     * @return the public suffix, {@code null} if none matched
     */
    public String getPublicSuffix(final String domain) {
        if (StringUtils.isEmpty(domain)) {
            return null;

        }
        PunycodeAutoDecoder punycode = new PunycodeAutoDecoder();
        String decodedDomain = punycode.recode(domain);

        Rule rule = index.findRule(decodedDomain);
        if (rule == null) {
            return null;

        }
        return punycode.decode(rule.match(decodedDomain));
    }

    /**
     * Returns whether a domain is a public suffix or not.
     *
     * Example: "com" is a public suffix, "example.com" isn't.
     *
     * This method is case insensitive.
     *
     * @param domain  the domain name, not null
     * @return {@code true} if the domain is a public suffix
     */
    public boolean isPublicSuffix(final String domain) {
        if (domain == null) {
            throw new IllegalArgumentException("The domain must not be null");

        }
        return domain.equals(getPublicSuffix(domain));
    }

    /**
     * Returns the character encoding of the public suffix list.
     *
     * @return the character encoding
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Returns the URL of the public suffix list.
     *
     * @return the URL
     */
    public URL getURL() {
        return url;
    }

    /**
     * Returns a list with all rules.
     *
     * @return the rule list
     */
    public List<Rule> getRules() {
        return index.getRules();
    }

}
