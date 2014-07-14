package de.malkusch.whoisServerList.publicSuffixList.index;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleComparator;

/**
 * Rule index.
 *
 * The {@link PublicSuffixListFactory} builds the {@code PublicSuffixList} with
 * the index defined with
 * the property {@link PublicSuffixListFactory#PROPERTY_INDEX}.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public abstract class Index implements Iterable<Rule> {

    /**
     * Initializes the index with all rules.
     *
     * @param rules Rule list
     * @see Parser#parse(java.io.InputStream, java.nio.charset.Charset)
     */
    public abstract void setRules(List<Rule> rules);

    /**
     * Finds a list of matching rules.
     *
     * This list may not include all matching rules, but must include the
     * prevailing rule.
     *
     * @param domain  domain name, may be null
     * @return list of matching rules, not null.
     */
    protected abstract Collection<Rule> findRules(String domain);

    /**
     * Returns all rules of this index.
     *
     * @return all rules, not null
     */
    public abstract List<Rule> getRules();

    @Override
    public Iterator<Rule> iterator() {
        return getRules().iterator();
    }

    /**
     * Finds the prevailing rule.
     *
     * @param domain  Domain name, null returns null
     * @return the prevailing rule, null if no rule matches
     */
    public final Rule findRule(final String domain) {
        try {
            if (StringUtils.isEmpty(domain)) {
                return null;

            }
            SortedSet<Rule> rules = new TreeSet<>(new RuleComparator());
            rules.addAll(findRules(domain));
            return rules.last();

        } catch (NoSuchElementException e) {
            return null;

        }
    }

}
