package de.malkusch.whoisServerList.publicSuffixList.util;

public class DomainUtil {

	/**
	 * Normalizes a domain name to lowercase
	 */
	static public String normalize(String domain) {
		return domain != null ? domain.toLowerCase() : null;
	}

}
