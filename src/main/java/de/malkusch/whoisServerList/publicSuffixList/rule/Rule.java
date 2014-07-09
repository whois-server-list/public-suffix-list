package de.malkusch.whoisServerList.publicSuffixList.rule;

public class Rule {
	
	private String rule;
	
	public Rule(String rule) {
		this.rule = rule;
	}

	public String getRule() {
		return rule;
	}

	@Override
	public String toString() {
		return rule;
	}
	
}
