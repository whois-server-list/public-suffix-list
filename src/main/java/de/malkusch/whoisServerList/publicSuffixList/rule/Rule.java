package de.malkusch.whoisServerList.publicSuffixList.rule;

import java.util.Arrays;

import net.jcip.annotations.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

/**
 * The Public Suffix rule.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
public final class Rule {

    /**
     * The default rule ("*").
     *
     * This rule applies if no other rule did match.
     */
    public static final Rule DEFAULT = new Rule("*");

    /**
     * The exception token.
     *
     * An exception rule begins with "!".
     */
    public static final char EXCEPTION_TOKEN = '!';

    /**
     * The wildcard label.
     */
    public static final String WILDCARD = "*";

    /**
     * The rule matcher.
     */
    private final RuleMatcher matcher;

    /**
     * Whether this is an exception rule or not.
     */
    private final boolean exceptionRule;

    /**
     * Initializes a normal rule.
     *
     * The pattern has no more exception token!
     *
     * @param pattern  the rule matching pattern
     */
    public Rule(final String pattern) {
        this(pattern, false);
    }

    /**
     * Initializes a rule.
     *
     * The pattern has no more exception token!
     *
     * @param pattern  the rule matching pattern
     * @param exceptionRule  whether this is an exception rule
     */
    public Rule(final String pattern, final boolean exceptionRule) {
        this.matcher = new RuleMatcher(pattern);
        this.exceptionRule = exceptionRule;
    }

    /**
     * Returns the label count of this rule.
     *
     * The label count is used for determining the prevailing rule.
     *
     * @return the label count
     * @see RuleComparator
     * @see Index#findRule(String)
     */
    public int getLabelCount() {
        return DomainUtil.splitLabels(matcher.getPattern()).length;
    }

    /**
     * Returns the rule pattern.
     *
     * The exception token is not included in the pattern!
     *
     * @return the rule pattern, not null.
     */
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
     * Returns if this rule is an exception rule.
     *
     * Exception rules are always prevailing rules.
     *
     * @return true if this is an exception rule
     */
    public boolean isExceptionRule() {
        return exceptionRule;
    }

    /**
     * Returns the matched public suffix of a domain.
     *
     * @param domain  the domain name, may be null
     * @return the public suffix for the domain name, or null if no match
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
            String[] reducedLabels
                = Arrays.copyOfRange(labels, 1, labels.length);

            return DomainUtil.joinLabels(reducedLabels);

        }
    }

}
