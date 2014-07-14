package de.malkusch.whoisServerList.publicSuffixList.rule;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

public class Rule {

    public static final Rule DEFAULT = new Rule("*");

    public static final char EXCEPTION_TOKEN = '!';

    public static final String WILDCARD = "*";

    private RuleMatcher matcher;

    private boolean exceptionRule;

    public Rule(final String pattern) {
        this.matcher = new RuleMatcher(pattern);
    }

    public int getLabelCount() {
        return DomainUtil.splitLabels(matcher.getPattern()).length;
    }

    public String getPattern() {
        return matcher.getPattern();
    }

    @Override
    public String toString() {
        String pattern = matcher.toString();
        return isExceptionRule() ? EXCEPTION_TOKEN + pattern : pattern;
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Set true for exception rules.
     *
     * @param exceptionRule whether this is an exception rule
     */
    public void setExceptionRule(final boolean exceptionRule) {
        this.exceptionRule = exceptionRule;
    }

    /**
     * Returns if this rule applies as an exception.
     */
    public boolean isExceptionRule() {
        return exceptionRule;
    }

    /**
     * Returns the match for a given domain.
     *
     * Returns null if the rule doesn't match.
     *
     * @param domain Domain name
     */
    public String match(final String domain) {
        if (domain == null) {
            return null;

        }
        String match = matcher.match(domain);
        if (match == null) {
            return null;

        }
        if (!isExceptionRule()) {
            return match;

        } else {
            String[] labels = DomainUtil.splitLabels(match);
            String[] reducedLabels = Arrays.copyOfRange(labels, 1, labels.length);
            return DomainUtil.joinLabels(reducedLabels);

        }
    }

}
