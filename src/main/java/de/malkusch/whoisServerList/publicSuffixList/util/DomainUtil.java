package de.malkusch.whoisServerList.publicSuffixList.util;

import java.util.Collection;

import net.jcip.annotations.ThreadSafe;

import org.apache.commons.lang3.StringUtils;

/**
 * Domain utility class.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@ThreadSafe
public final class DomainUtil {

    /**
     * Private utility class constructor.
     */
    private DomainUtil() {
    }

    /**
     * Splits a domain or pattern into its labels.
     *
     * Splitting is done at ".".
     *
     * @param domain  the domain name or rule pattern, null returns null
     * @return the domain or rule label, or null
     */
    public static String[] splitLabels(final String domain) {
        if (domain == null) {
            return null;

        }
        if (domain.isEmpty()) {
            return new String[]{};

        }
        return domain.split("\\.");
    }

    /**
     * Joins labels to a domain name or rule pattern.
     *
     * Joining is done with ".".
     *
     * @param labels  the domain or rule labels, not null
     * @return the domain name or rule pattern
     */
    public static String joinLabels(final Collection<String> labels) {
        return joinLabels(labels.toArray(new String[]{}));
    }

    /**
     * Joins labels to a domain name or rule pattern.
     *
     * Joining is done with ".".
     *
     * @param labels  the domain or rule labels, not null
     * @return the domain name or rule pattern
     */
    public static String joinLabels(final String[] labels) {
        return StringUtils.join(labels, '.');
    }

}
