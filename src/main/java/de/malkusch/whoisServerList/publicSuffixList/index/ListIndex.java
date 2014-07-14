package de.malkusch.whoisServerList.publicSuffixList.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * List based implementation with O(n) complexity.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public final class ListIndex extends Index {

    /**
     * all rules.
     */
    private List<Rule> rules;

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
    public void setRules(final List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public List<Rule> getRules() {
        return rules;
    }

}
