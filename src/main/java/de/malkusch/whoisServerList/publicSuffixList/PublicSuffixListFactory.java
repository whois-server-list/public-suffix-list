package de.malkusch.whoisServerList.publicSuffixList;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.tree.TreeIndex;
import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * Factory for PublicSuffixList.
 * 
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 * @see PublicSuffixList
 */
public class PublicSuffixListFactory {

	/**
	 * URL of the Public Suffix List.
	 * 
	 * @see PublicSuffixList#getURL()
	 */
	final public static String PROPERTY_URL = "psl.url";
	
	/**
	 * Character encoding of the list
	 * 
	 * @see PublicSuffixList#getCharset()
	 */
	final public static String PROPERTY_CHARSET = "psl.charset";
	
	/**
	 * The factory will load the Public Suffix List from this file.
	 */
	final public static String PROPERTY_LIST_FILE = "psl.file";
	
	/**
	 * Index implementation.
	 * 
	 * @see Index
	 */
	final public static String PROPERTY_INDEX = "psl.index";
	
	/**
	 * Location of the default properties.
	 * 
	 * @see #build()
	 */
	final public static String PROPERTY_FILE = "/psl.properties";
	
	public Properties getDefaults() throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream(PROPERTY_FILE));
		return properties;
	}

	/**
	 * Builds a PublicSuffixList with custom properties.
	 * 
	 * It is a good idea to load the default properties at {@link #PROPERTY_FILE} and
	 * overwrite keys as required.
	 */
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
			
			PublicSuffixList list = new PublicSuffixList(index, url, charset);
			
			return list;
			
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException(e);
			
		}
	}

	/**
	 * Builds a PublicSuffixList with the default properties.
	 * 
	 * The default properties are included in the jar file at /psl.properties.
	 * This list uses the bundled list file and a {@link TreeIndex}.
	 * 
	 * @see #PROPERTY_FILE
	 */
	public PublicSuffixList build() throws IOException {
		try {
			return build(getDefaults());
			
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
			
		}
	}

}
