package de.malkusch.whoisServerList.publicSuffixList;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;
import de.malkusch.whoisServerList.publicSuffixList.util.PunnycodeAutoDecoder;

/**
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 * @see PublicSuffixListFactory
 */
public class PublicSuffixList {
	
	private URL url;
	
	private Charset charset;
	
	private Index index;
	
	/**
	 * @see PublicSuffixListFactory#build()
	 */
	protected PublicSuffixList(Index index, URL url, Charset charset) {
		this.index = index;
		this.url = url;
		this.charset = charset;
	}
	
	/**
	 * Gets the registrable domain or null.
	 */
	public String getRegistrableDomain(String domain) {
		if (StringUtils.isEmpty(domain)) {
			return null;
			
		}
		/*
		 * Mozilla's test cases implies that leading dots result to no registrable domain
		 * @see http://mxr.mozilla.org/mozilla-central/source/netwerk/test/unit/data/test_psl.txt?raw=1
		 */
		if (domain.charAt(0) == '.') {
			return null;
			
		}
		PunnycodeAutoDecoder punnycode = new PunnycodeAutoDecoder();
		domain = punnycode.decode(domain);
		
		String suffix = getPublicSuffix(domain);
		if (StringUtils.equals(domain, suffix)) {
			return null;
			
		}
		String[] suffixLabels = DomainUtil.splitLabels(getPublicSuffix(domain));
		if (suffixLabels == null) {
			return null;
			
		}
		String[] labels = DomainUtil.splitLabels(domain);
		int offset = labels.length - suffixLabels.length - 1;
		String registrableDomain = DomainUtil.joinLabels(Arrays.copyOfRange(labels, offset, labels.length));
		
		return punnycode.recode(registrableDomain);
	}
	
	/**
	 * Returns whether a domain is registrable.
	 */
	public boolean isRegistrable(String domain) {
		if (domain == null) {
			throw new NullPointerException();
			
		}
		return domain.equals(getRegistrableDomain(domain));
	}
	
	/**
	 * Returns the public suffix from a domain or null.
	 * 
	 * If the domain is already a public suffix, it will be returned unchanged.
	 */
	public String getPublicSuffix(String domain) {
		if (StringUtils.isEmpty(domain)) {
			return null;
			
		}
		PunnycodeAutoDecoder punnycode = new PunnycodeAutoDecoder();
		domain = punnycode.recode(domain);
		
		Rule rule = index.findRule(domain);
		if (rule == null) {
			return null;
			
		}
		return punnycode.decode(rule.match(domain));
	}
	
	/**
	 * Returns whether a domain is a public suffix or not.
	 * 
	 * Example: "com" is a public suffix, "example.com" isn't. 
	 */
	public boolean isPublicSuffix(String domain) {
		if (domain == null) {
			throw new NullPointerException();
			
		}
		return domain.equals(getPublicSuffix(domain));
	}
	
	/**
	 * Returns the charset of the public suffix list
	 */
	public Charset getCharset() {
		return charset;
	}
	
	/**
	 * The URL of the public suffix list
	 */
	public URL getURL() {
		return url;
	}
	
}
