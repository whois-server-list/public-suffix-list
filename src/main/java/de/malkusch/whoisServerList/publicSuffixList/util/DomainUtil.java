package de.malkusch.whoisServerList.publicSuffixList.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;


public class DomainUtil {

	/**
	 * Splits a domain or pattern into its labels.
	 */
	static public String[] splitLabels(String domain) {
		if (domain == null) {
			return null;
			
		}
		if (domain.isEmpty()) {
			return new String[]{};
			
		}
		return domain.split("\\.");
	}
	
	/**
	 * Joins labels to a domain or pattern.
	 */
	static public String joinLabels(Collection<String> labels) {
		return joinLabels(labels.toArray(new String[]{}));
	}
	
	/**
	 * Joins labels to a domain or pattern.
	 */
	static public String joinLabels(String[] labels) {
		return StringUtils.join(labels, '.');
	}

}
