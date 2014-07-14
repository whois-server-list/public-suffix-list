package de.malkusch.whoisServerList.publicSuffixList.index.array;

import java.util.List;

import net.jcip.annotations.Immutable;
import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.IndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * Builds an array based implementation with O(n) complexity.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 * @see ArrayIndex
 */
@Immutable
public final class ArrayIndexFactory implements IndexFactory {

    @Override
    public Index build(final List<Rule> rules) {
        return new ArrayIndex(rules);
    }

}
