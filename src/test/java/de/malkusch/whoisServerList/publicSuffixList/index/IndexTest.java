package de.malkusch.whoisServerList.publicSuffixList.index;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.test.util.TestUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class IndexTest {

    public static Collection<IndexFactory> CASES() {
        return TestUtil.getTestIndexFactories();
    }

    @ParameterizedTest
    @MethodSource("CASES")
    public void testFindRuleFromSubtreeMatch(IndexFactory factory) {
        List<Rule> rules = TestUtil.convertRules(
                "b.c.d",
                "a.*.d");
        Index index = factory.build(rules);

        assertEquals("b.c.d", index.findRule("b.c.d").getPattern());
        assertEquals("a.*.d", index.findRule("a.c.d").getPattern());
    }

    @ParameterizedTest
    @MethodSource("CASES")
    public void testFindRule(IndexFactory factory) {
        List<Rule> rules = TestUtil.convertRules(
                "net",
                "com",
                "a.com",
                "a.org",
                "org",
                "*.ck",
                "!www.ck");
        Index index = factory.build(rules);

        assertNull(index.findRule(null));
        assertNull(index.findRule(""));
        assertNull(index.findRule("invalid"));
        assertNull(index.findRule("x.invalid"));

        assertEquals("net", index.findRule("net").getPattern());
        assertEquals("net", index.findRule("x.net").getPattern());
        assertEquals("net", index.findRule("x.x.net").getPattern());
        assertEquals("com", index.findRule("x.com").getPattern());
        assertEquals("com", index.findRule("com").getPattern());
        assertEquals("com", index.findRule("x.com").getPattern());
        assertEquals("a.com", index.findRule("a.com").getPattern());
        assertEquals("a.com", index.findRule("x.a.com").getPattern());
        assertEquals("*.ck", index.findRule("a.ck").getPattern());
        assertEquals("*.ck", index.findRule("x.a.ck").getPattern());
        assertEquals("www.ck", index.findRule("www.ck").getPattern());
        assertEquals("www.ck", index.findRule("x.www.ck").getPattern());
    }
}
