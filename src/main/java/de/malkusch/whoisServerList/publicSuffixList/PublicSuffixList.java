package de.malkusch.whoisServerList.publicSuffixList;

import java.net.URL;
import java.nio.charset.Charset;

import de.malkusch.whoisServerList.publicSuffixList.exception.SuffixDomainException;

/**
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 * @see PublicSuffixListFactory
 */
public class PublicSuffixList {
	
	private URL url;
	
	private Charset charset;
	
	/**
	 * @see PublicSuffixListFactory#build()
	 */
	protected PublicSuffixList(URL url, Charset charset) {
		this.url = url;
		this.charset = charset;
	}
	
	/**
	 * Gets the registrable domain.
	 * 
	 * @throws SuffixDomainException The domain is already a public suffix
	 */
	public String getRegistrableDomain(String domain) throws SuffixDomainException {
		if (domain == null) {
			return null;
			
		}
		
		//TODO
		return null;
	}
	
	/**
	 * Returns whether a domain is registrable.
	 */
	public boolean isRegistrable(String domain) {
		if (domain == null) {
			throw new NullPointerException();
			
		}
		
		//TODO
		return false;
	}
	
	/**
	 * Returns the public suffix from a domain.
	 * 
	 * If the domain is already a public suffix, it will be returned unchanged.
	 */
	public String getPublicSuffix(String domain) {
		if (domain == null) {
			throw new NullPointerException();
			
		}
		
		//TODO
		return null;
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
		
		return false;
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
