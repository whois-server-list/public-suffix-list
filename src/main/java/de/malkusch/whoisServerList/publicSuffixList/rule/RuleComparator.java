package de.malkusch.whoisServerList.publicSuffixList.rule;

import java.io.Serializable;
import java.util.Comparator;

import net.jcip.annotations.Immutable;

/**
 * Orders prevailing rules higher.
 *
 * The rule with the highest {@link Rule#getLabelCount()} is the
 * prevailing rule. An exception rule is always the prevailing rule.
 *
 * @author markus@malkusch.de
 *
 * @see Rule#isExceptionRule()
 * @see Rule#getLabelCount()
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
public final class RuleComparator implements Comparator<Rule>, Serializable {

    /**
     * Version number.
     *
     * @see Serializable
     */
    private static final long serialVersionUID = -3222683638595906734L;

    @Override
    public int compare(final Rule rule1, final Rule rule2) {
        if (rule1.isExceptionRule() && rule2.isExceptionRule()) {
            if (!rule1.equals(rule2)) {
                throw new IllegalArgumentException(
                    "You can't compare two exception rules.");

            }
            return 0;

        }
        if (rule1.isExceptionRule()) {
            return 1;

        }
        if (rule2.isExceptionRule()) {
            return -1;

        }
        return Integer.compare(rule1.getLabelCount(), rule2.getLabelCount());
    }

}
