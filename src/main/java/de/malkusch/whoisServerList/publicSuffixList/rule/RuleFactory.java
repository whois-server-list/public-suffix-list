package de.malkusch.whoisServerList.publicSuffixList.rule;

import net.jcip.annotations.Immutable;

/**
 * The rule factory.
 *
 * This factory builds {@link Rule} objects from rule strings.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
public final class RuleFactory {

    /**
     * Builds a {@code Rule}.
     *
     * The rule pattern is as it was in the Public Suffix List.
     * I.e. including the wildcards and exception token.
     *
     * @param pattern  the rule pattern, not null
     * @return the rule, not null
     */
    public Rule build(final String pattern) {
        boolean exceptionRule = pattern.charAt(0) == Rule.EXCEPTION_TOKEN;
        String matchPattern = exceptionRule ? pattern.substring(1) : pattern;

        Rule rule = new Rule(matchPattern, exceptionRule);

        return rule;
    }

}
