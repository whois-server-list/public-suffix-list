package de.malkusch.whoisServerList.publicSuffixList.rule;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

public class Rule {
	
	final public static Rule DEFAULT = new Rule("*"); 

	final public static char EXCEPTION_TOKEN = '!';
	
	final public static String WILDCARD = "*";
	
	private RuleMatcher matcher;
	
	private boolean exceptionRule;
	
	public Rule(String pattern) {
		this.matcher = new RuleMatcher(pattern);
	}
	
	public int getLabelCount() {
		return DomainUtil.splitLabels(matcher.getPattern()).length;
	}
	
	public String getPattern() {
		return matcher.getPattern();
	}

	@Override
	public String toString() {
		String pattern = matcher.toString();
		return isExceptionRule() ? EXCEPTION_TOKEN + pattern : pattern;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	/**
	 * Set true for exception rules.
	 */
	public void setExceptionRule(boolean exceptionRule) {
		this.exceptionRule = exceptionRule;
	}
	
	/**
	 * Returns if this rule applies as an exception
	 */
	public boolean isExceptionRule() {
		return exceptionRule;
	}
	
	/**
	 * Returns the match for a given domain.
	 * 
	 * Returns null if the rule doesn't match.
	 */
	public String match(String domain) {
		if (domain == null) {
			return null;
			
		}
		String match = matcher.match(domain);
		if (match == null) {
			return null;
			
		}
		if (! isExceptionRule()) {
			return match;
			
		} else {
			String[] labels = DomainUtil.splitLabels(match);
			String[] reducedLabels = Arrays.copyOfRange(labels, 1, labels.length);
			return DomainUtil.joinLabels(reducedLabels);
			
		}
	}
	
}
