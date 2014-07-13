package de.malkusch.whoisServerList.publicSuffixList;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

public class PublicSuffixListFactory {

	final public static String PROPERTY_URL = "psl.url";
	final public static String PROPERTY_CHARSET = "psl.charset";
	final public static String PROPERTY_LIST_FILE = "psl.file";
	final public static String PROPERTY_INDEX = "psl.index";
	final public static String PROPERTY_FILE = "/psl.properties";

	public PublicSuffixList build(Properties properties) throws IOException, ClassNotFoundException {
		try {
			URL url = new URL(properties.getProperty(PROPERTY_URL));
			Charset charset = Charset.forName(properties.getProperty(PROPERTY_CHARSET));
			
			Parser parser = new Parser();
			List<Rule> rules = parser.parse(getClass().getResourceAsStream(properties.getProperty(PROPERTY_LIST_FILE)), charset);
			
			// add default rule
			rules.add(Rule.DEFAULT);
			
			Index index = (Index) Class.forName(properties.getProperty(PROPERTY_INDEX)).newInstance();
			index.setRules(rules);
			
			return new PublicSuffixList(index, url, charset);
			
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException(e);
			
		}
	}

	public PublicSuffixList build() throws IOException {
		try {
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream(PROPERTY_FILE));
			return build(properties);
			
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
			
		}
	}

}
