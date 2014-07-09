package de.malkusch.whoisServerList.publicSuffixList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

public class PublicSuffixListFactory {

	final public static String PROPERTY_URL = "psl.url";
	final public static String PROPERTY_CHARSET = "psl.charset";
	final public static String PROPERTY_FILE = "/psl.properties";

	public PublicSuffixList build(Properties properties) throws MalformedURLException {
		URL url = new URL(properties.getProperty(PROPERTY_URL));
		Charset charset = Charset.forName(properties.getProperty(PROPERTY_CHARSET));
		
		return new PublicSuffixList(url, charset);
	}

	public PublicSuffixList build() throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream(PROPERTY_FILE));
		return build(properties);
	}

}
