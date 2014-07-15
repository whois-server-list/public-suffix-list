package de.malkusch.whoisServerList.publicSuffixList.rule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleFactory;

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

    @RunWith(Parameterized.class)
    static public class ToStringTest {

        @Parameter
        public String pattern;

        @Parameters
        static public Iterable<String[]> providePatterns() {
            List<String[]> cases = new ArrayList<>();
            cases.add(new String[] {"de"});
            cases.add(new String[] {"*.de"});
            cases.add(new String[] {"!*.de"});
            cases.add(new String[] {"!test.de"});
            cases.add(new String[] {"!test.*.de"});
            return cases;
        }

        private Rule rule;

        @Before
        public void setRule() {
            RuleFactory factory = new RuleFactory();
            rule = factory.build(pattern);
        }

        @Test
        public void testToString() {
            assertEquals(pattern, rule.toString());
        }

    }

}
