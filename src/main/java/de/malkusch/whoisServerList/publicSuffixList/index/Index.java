package de.malkusch.whoisServerList.publicSuffixList.index;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleComparator;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Rule index.
 * <p>
 * The {@link PublicSuffixListFactory} builds the {@code PublicSuffixList} with
 * the index defined with
 * the property {@link PublicSuffixListFactory#PROPERTY_INDEX}.
 * <p>
 * Index implementations must be thread-safe.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public abstract class Index {

    /**
     * Finds a list of matching rules.
     * <p>
     * This list may not include all matching rules, but must include the
     * prevailing rule.
     *
     * @param domain the domain name, may be null
     * @return the list of matching rules, not null.
     */
    protected abstract Collection<Rule> findRules(String domain);

    /**
     * Returns all rules of this index.
     *
     * @return the rules, not null
     */
    public abstract List<Rule> getRules();

    /**
     * Finds the prevailing rule.
     *
     * @param domain the domain name, null returns null
     * @return the prevailing rule, null if no rule matches
     */
    public final Rule findRule(final String domain) {
        try {
            if (StringUtils.isEmpty(domain)) {
                return null;

            }
            return Collections.max(findRules(domain), new RuleComparator());

        } catch (NoSuchElementException e) {
            return null;

        }
    }

}
