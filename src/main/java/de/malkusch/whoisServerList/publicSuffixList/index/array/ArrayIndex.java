package de.malkusch.whoisServerList.publicSuffixList.index.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * Array based implementation with O(n) complexity.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
final class ArrayIndex extends Index {

    /**
     * All rules.
     */
    private final Rule[] rules;

    /**
     * Sets the rules.
     *
     * @param rules  the rules, not null
     */
    ArrayIndex(final List<Rule> rules) {
        this.rules = rules.toArray(new Rule[]{});
    }

    @Override
    protected Collection<Rule> findRules(final String domain) {
        List<Rule> matches = new ArrayList<>();
        for (Rule rule : rules) {
            if (rule.match(domain) != null) {
                matches.add(rule);

            }
        }
        return matches;
    }

    @Override
    public List<Rule> getRules() {
        return Arrays.asList(rules);
    }

}
