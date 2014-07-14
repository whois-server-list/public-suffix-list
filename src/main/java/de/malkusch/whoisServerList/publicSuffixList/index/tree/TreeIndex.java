package de.malkusch.whoisServerList.publicSuffixList.index.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.jcip.annotations.Immutable;

import org.apache.commons.lang3.StringUtils;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * Tree based implementation with O(log(n)) complexity.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
final class TreeIndex extends Index {

    /**
     * The root.
     */
    private final ImmutableNode root;

    /**
     * Sets the tree root.
     *
     * @param root  the root, not null
     */
    TreeIndex(final ImmutableNode root) {
        this.root = root;
    }

    /**
     * Returns the canonical label.
     *
     * I.e. "DE" equals "de".
     *
     * @param label  the label in any case, null returns null
     * @return the canonical label, or null
     */
    static String getCanonicalLabel(final String label) {
        return StringUtils.lowerCase(label);
    }

    @Override
    protected Collection<Rule> findRules(final String domain) {
        Collection<Rule> rules = new ArrayList<>();
        for (ImmutableNode node : root.findNodes(getCanonicalLabel(domain))) {
            Rule rule = node.getRule();
            if (rule != null) {
                rules.add(rule);

            }
        }
        return rules;
    }

    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        for (ImmutableNode node : root.getDescendants()) {
            Rule rule = node.getRule();
            if (rule != null) {
                rules.add(rule);

            }
        }
        return rules;
    }

}
