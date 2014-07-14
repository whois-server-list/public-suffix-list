package de.malkusch.whoisServerList.publicSuffixList.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;


public final class DomainUtil {

    private DomainUtil() {
    }

    /**
     * Splits a domain or pattern into its labels.
     *
     * @param domain Domain name or Rule pattern
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
     * Joins labels to a domain or pattern.
     *
     * @param labels Domain or rule labels
     */
    public static String joinLabels(final Collection<String> labels) {
        return joinLabels(labels.toArray(new String[]{}));
    }

    /**
     * Joins labels to a domain or pattern.
     *
     * @param labels Domain or rule labels
     */
    public static String joinLabels(final String[] labels) {
        return StringUtils.join(labels, '.');
    }

}
