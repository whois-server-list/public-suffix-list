package de.malkusch.whoisServerList.publicSuffixList.rule;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.malkusch.whoisServerList.publicSuffixList.rule.label.LabelMatcher;
import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

public class RuleMatcher {
	
	private String[] reversedLabels;
	
	/**
	 * set a copy of the labels.
	 */
	public RuleMatcher(String[] labels) {
		this.reversedLabels = labels.clone();
		ArrayUtils.reverse(reversedLabels);
	}
	
	/**
	 * Set the labels
	 */
	public RuleMatcher(String labels) {
		this(DomainUtil.splitLabels(labels));
	}
	
	/**
	 * Return the matched suffix or null
	 */
	public String match(String domain) {
		if (domain == null) {
			return null;
			
		}
		
		String[] reversedDomainLabels = DomainUtil.splitLabels(domain);
		ArrayUtils.reverse(reversedDomainLabels);
		if (reversedDomainLabels.length < reversedLabels.length) {
			return null;
			
		}
		
		String[] reversedMatchedLabels = new String[reversedLabels.length];
		for (int i = 0; i < reversedLabels.length; i++) {
			if (i > reversedDomainLabels.length) {
				return null;
				
			}
			String matchLabel = reversedLabels[i];
			String domainLabel = reversedDomainLabels[i];
			
			LabelMatcher matcher = new LabelMatcher(matchLabel);
			if (! matcher.isMatch(domainLabel)) {
				return null;
				
			}
			reversedMatchedLabels[i] = domainLabel;
			
		}
		ArrayUtils.reverse(reversedMatchedLabels);
		return DomainUtil.joinLabels(reversedMatchedLabels);
	}
	
	public String getPattern() {
		String[] labels = reversedLabels.clone();
		ArrayUtils.reverse(labels);
		return DomainUtil.joinLabels(labels);
	}
	
	@Override
	public String toString() {
		return getPattern();
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
