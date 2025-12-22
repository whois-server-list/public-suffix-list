package de.malkusch.whoisServerList.publicSuffixList.index.array;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.IndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

import java.util.List;

/**
 * Builds an array based implementation with O(n) complexity.
 *
 * @author markus@malkusch.de
 * @see ArrayIndex
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public final class ArrayIndexFactory implements IndexFactory {

    @Override
    public Index build(final List<Rule> rules) {
        return new ArrayIndex(rules);
    }

}
