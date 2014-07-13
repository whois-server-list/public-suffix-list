package de.malkusch.whoisServerList.publicSuffixList.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

public class ListIndex extends Index {
	
	private List<Rule> rules;
	
	@Override
	protected Collection<Rule> findRules(String domain) {
		List<Rule> matches = new ArrayList<>();
		for (Rule rule : rules) {
			if (rule.match(domain) != null) {
				matches.add(rule);
				
			}
		}
		return matches;
	}

	@Override
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	@Override
	public List<Rule> getRules() {
		return rules;
	}

}
