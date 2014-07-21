package de.malkusch.whoisServerList.publicSuffixList.index;

import java.util.List;

import javax.annotation.concurrent.ThreadSafe;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * The index factory builds an {@code Index}.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 * @since 2.0.0
 */
@ThreadSafe
public interface IndexFactory {

    /**
     * Builds an index from a rule list.
     *
     * @param rules  the rule list, not null
     * @return the index, not null
     */
    Index build(List<Rule> rules);

}
