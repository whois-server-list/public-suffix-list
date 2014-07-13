package de.malkusch.whoisServerList.publicSuffixList.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import static de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory.*;

public class TestUtil {
	
	static public Properties getDefaultProperties() throws IOException {
		PublicSuffixListFactory factory = new PublicSuffixListFactory();
		return factory.getDefaults();
	}
	
	static public Charset getDefaultCharset() throws IOException {
		return Charset.forName(getDefaultProperties().getProperty(PROPERTY_CHARSET));
	}
	
	static public InputStream getDefaultListFile() throws IOException {
		String file = getDefaultProperties().getProperty(PROPERTY_LIST_FILE);
		return TestUtil.class.getResourceAsStream(file);
	}
	
	static public List<Rule> getDefaultParsedRules() throws IOException {
		Parser parser = new Parser();
		return parser.parse(getDefaultListFile(), getDefaultCharset());
	}

}
