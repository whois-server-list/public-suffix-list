package de.malkusch.whoisServerList.publicSuffixList;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public class PublicSuffixList {

	private URL url;
	
	private Properties properties;
	
	public PublicSuffixList() throws IOException {
		properties = new Properties();
		properties.load(getClass().getResourceAsStream("/psl.properties"));
		
		url = new URL(properties.getProperty("psl.url"));
	}
	
	/**
	 * The URL of the public suffix list
	 */
	public URL getURL() {
		return url;
	}
	
}
