package de.malkusch.whoisServerList.publicSuffixList.rule.label;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

public class LabelMatcher {

    private String pattern;

    public LabelMatcher(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }

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
