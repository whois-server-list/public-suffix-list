package de.malkusch.whoisServerList.publicSuffixList.index;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleComparator;

abstract public class Index implements Iterable<Rule> {
	
	/**
	 * Initializes the index with all rules.
	 */
	abstract public void setRules(List<Rule> rules);
	
	/**
	 * Finds all matching rules.
	 */
	abstract protected Collection<Rule> findRules(String domain);
	
	abstract public List<Rule> getRules();
	
	@Override
	public Iterator<Rule> iterator() {
		return getRules().iterator();
	}
	
	/**
	 * Finds the prevailing rule or null.
	 */
	public Rule findRule(String domain) {
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
