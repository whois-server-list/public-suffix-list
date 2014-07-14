package de.malkusch.whoisServerList.publicSuffixList.util;

import static de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory.PROPERTY_CHARSET;
import static de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory.PROPERTY_LIST_FILE;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.IndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.index.array.ArrayIndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.index.tree.TreeIndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleFactory;
import de.malkusch.whoisServerList.publicSuffixList.test.GetRegistrableDomainTest;

public class TestUtil {

    private TestUtil() {
    }
    
    /**
     * Returns the list of Mozilla's checkPublicSuffix test cases.
     * 
     * @return the list with the tupel (domain, registrableDomain)
     * @throws IOException if reading the test cases fails
     */
    public static List<String[]> getCheckPublicSuffixCases() throws IOException {
        List<String[]> cases = new ArrayList<>();
        
        InputStream stream 
            = GetRegistrableDomainTest.class.getResourceAsStream("/checkPublicSuffix.txt");
        
        BufferedReader reader
            = new BufferedReader(new InputStreamReader(stream));
        
        Pattern pattern 
            = Pattern.compile("^checkPublicSuffix\\((\\S+),\\s*(\\S+)\\);\\s*$");
        
        String line;
        while((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (! matcher.matches()) {
                continue;

            }
            String domain = parseArgument(matcher.group(1));
            String registrableDomain = parseArgument(matcher.group(2));

            cases.add(new String[]{ domain, registrableDomain });
            
        }
        return cases;
    }
    
    static private String parseArgument(String argument) {
        if (argument.equals("null")) {
            return null;

        }
        Pattern pattern = Pattern.compile("'(.+)'");
        Matcher matcher = pattern.matcher(argument);
        assertTrue(matcher.matches());

        return matcher.group(1);
    }

    public static List<Rule> convertRules(String...rules) {
        RuleFactory factory = new RuleFactory();
        List<Rule> list = new ArrayList<>();
        for (String rule : rules) {
            list.add(factory.build(rule));

        }
        return list;
    }

    public static Collection<Index[]> getTestIndexes() throws IOException {
        Collection<Index[]> cases = new ArrayList<>();
        for (IndexFactory[] factory : getTestIndexFactories()) {
            cases.add(new Index[]{ factory[0].build(getDefaultParsedRules()) });

        }
        return cases;
    }

    public static Collection<IndexFactory[]> getTestIndexFactories() {
        Collection<IndexFactory[]> cases = new ArrayList<>();

        cases.add(new IndexFactory[]{ new ArrayIndexFactory() });
        cases.add(new IndexFactory[]{ new TreeIndexFactory() });

        return cases;
    }

    public static Properties getDefaultProperties() throws IOException {
        PublicSuffixListFactory factory = new PublicSuffixListFactory();
        return factory.getDefaults();
    }

    public static Charset getDefaultCharset() throws IOException {
        return Charset.forName(getDefaultProperties().getProperty(PROPERTY_CHARSET));
    }

    public static InputStream getDefaultListFile() throws IOException {
        String file = getDefaultProperties().getProperty(PROPERTY_LIST_FILE);
        return TestUtil.class.getResourceAsStream(file);
    }

    public static List<Rule> getDefaultParsedRules() throws IOException {
        Parser parser = new Parser();
        return parser.parse(getDefaultListFile(), getDefaultCharset());
    }

}
