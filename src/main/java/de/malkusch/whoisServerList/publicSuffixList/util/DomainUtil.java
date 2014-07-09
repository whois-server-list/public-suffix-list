package de.malkusch.whoisServerList.publicSuffixList.util;


public class DomainUtil {

	/**
	 * Normalizes a domain name to lowercase
	 */
	static public String normalize(String domain) {
		return domain != null ? domain.toLowerCase() : null;
	}
	
	/**
	 * Splits a domain into its parts.
	 */
	static public String[] split(String domain) {
		if (domain == null) {
			return null;
			
		}
		domain = normalize(domain);
		if (domain.isEmpty()) {
			return new String[]{};
			
		}
		return domain.split("\\.");
	}

}
