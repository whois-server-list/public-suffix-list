package de.malkusch.whoisServerList.publicSuffixList.rule;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The label matcher.
 * <p>
 * The matcher is case insensitive.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
final class LabelMatcher {

    /**
     * The label pattern.
     */
    private final String pattern;

    /**
     * Sets the label pattern.
     *
     * @param pattern the label pattern, not null
     */
    LabelMatcher(final String pattern) {
        this.pattern = pattern;
    }

    /**
     * Matches a label.
     * <p>
     * Empty labels or null never match. Matching is case insensitive.
     *
     * @param label the label, may be null
     * @return {@code true} if the label matches
     */
    boolean isMatch(final String label) {
        if (StringUtils.isEmpty(label)) {
            return false;

        }
        if (pattern.equals(Rule.WILDCARD)) {
            return true;

        }
        return pattern.equalsIgnoreCase(label);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return pattern;
    }

}
