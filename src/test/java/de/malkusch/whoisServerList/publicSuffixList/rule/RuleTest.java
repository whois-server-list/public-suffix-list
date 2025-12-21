package de.malkusch.whoisServerList.publicSuffixList.rule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class RuleTest {

    @Test
    public void testMatch() {
        RuleFactory factory = new RuleFactory();
        {
            Rule rule = factory.build("net");

            assertNull(rule.match(null));
            assertNull(rule.match(""));
            assertNull(rule.match("com"));

            assertEquals("net", rule.match("net"));
            assertEquals("net", rule.match("example.net"));
            assertEquals("net", rule.match("www.example.net"));
        }
        {
            Rule rule = factory.build("!www.ck");

            assertNull(rule.match(null));
            assertNull(rule.match("ck"));
            assertNull(rule.match("x.ck"));

            assertEquals("ck", rule.match("www.ck"));
            assertEquals("ck", rule.match("x.www.ck"));
        }
        {
            Rule rule = factory.build("*.ck");

            assertEquals("a.ck", rule.match("a.ck"));
            assertEquals("a.ck", rule.match("x.a.ck"));
        }
    }

    static public class ToStringTest {

        @ParameterizedTest
        @ValueSource(strings = {"de", "*.de", "!*.de", "!test.de", "!test.*.de"})
        public void testToString(String pattern) {
            RuleFactory factory = new RuleFactory();
            Rule rule = factory.build(pattern);
            assertEquals(pattern, rule.toString());
        }
    }
}
