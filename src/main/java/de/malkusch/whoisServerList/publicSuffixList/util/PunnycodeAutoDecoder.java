package de.malkusch.whoisServerList.publicSuffixList.util;

import java.net.IDN;

public class PunnycodeAutoDecoder {
	
	private boolean decoded;
	
	public String decode(String domain) {
		if (domain == null) {
			return null;
			
		}
		String asciiDomain = IDN.toUnicode(domain);
		decoded = ! asciiDomain.equals(domain);
		return asciiDomain;
	}
	
	public String recode(String domain) {
		return decoded ? IDN.toASCII(domain) : domain;
	}
	
	public boolean isConverted() {
		return decoded;
	}
	
}
