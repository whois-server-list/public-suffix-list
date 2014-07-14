package de.malkusch.whoisServerList.publicSuffixList.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;
import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;

@RunWith(Parameterized.class)
public class GetRegistrableDomainTest {

    @Parameter(0)
    public String domain;

    @Parameter(1)
    public String registrableDomain;

    @Parameter(2)
    public Properties properties;

    @Parameters
    static public Collection<Object[]> readMozillasTestCases() throws IOException {
        List<Object[]> cases = new ArrayList<>();

        InputStream stream = GetRegistrableDomainTest.class.getResourceAsStream("/checkPublicSuffix.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        Pattern pattern = Pattern.compile("^checkPublicSuffix\\((\\S+),\\s*(\\S+)\\);\\s*$");

        String line;
        while((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (! matcher.matches()) {
                continue;

            }
            String domain = parseArgument(matcher.group(1));
            String registrableDomain = parseArgument(matcher.group(2));

            for (Properties properties : PublicSuffixListTest.getPropertyCases()) {
                cases.add(new Object[]{ domain, registrableDomain, properties });

            }
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

    @Test
    public void testGetRegistrableDomain() throws IOException, ClassNotFoundException {
        PublicSuffixList psl = new PublicSuffixListFactory().build(properties);

        String actual = StringUtils.lowerCase(psl.getRegistrableDomain(domain));
        String expected = StringUtils.lowerCase(registrableDomain);
        assertEquals(
            String.format("%s should return %s, but was %s", domain, expected, actual),
            expected,
            actual
        );
    }

}