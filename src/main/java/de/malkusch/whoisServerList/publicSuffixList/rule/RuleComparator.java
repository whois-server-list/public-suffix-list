package de.malkusch.whoisServerList.publicSuffixList.rule;

import java.util.Comparator;

/**
 * Orders prevailing rules higher.
 * 
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public class RuleComparator implements Comparator<Rule> {

	@Override
	public int compare(Rule rule1, Rule rule2) {
		if (rule1.isExceptionRule() && rule2.isExceptionRule()) {
			if (! rule1.equals(rule2)) {
				throw new IllegalArgumentException("You can't compare two exception rules.");
				
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
