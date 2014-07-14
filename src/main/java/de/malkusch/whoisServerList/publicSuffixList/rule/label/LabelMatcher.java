package de.malkusch.whoisServerList.publicSuffixList.rule.label;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * The label matcher.
 *
 * The matcher is case insensitive.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public final class LabelMatcher {

    /**
     * The label pattern.
     */
    private String pattern;

    /**
     * Sets the label pattern.
     *
     * @param pattern  the label pattern, not null
     */
    public LabelMatcher(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }

    /**
     * Matches a label.
     *
     * Empty labels or null never match. Matching is case insensitive.
     *
     * @param label  the label, may be null
     * @return  true if the label matches
     */
    public boolean isMatch(final String label) {
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

}
