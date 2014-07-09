package de.malkusch.whoisServerList.publicSuffixList.rule;

public class RuleFactory {
	
	public Rule build(String rule) {
		return new Rule(rule);
	}

}
