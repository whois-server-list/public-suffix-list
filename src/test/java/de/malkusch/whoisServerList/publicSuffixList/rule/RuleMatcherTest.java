package de.malkusch.whoisServerList.publicSuffixList.rule;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RuleMatcherTest {

    public static Collection<Arguments> CASES() {
        ArrayList<Arguments> cases = new ArrayList<>();

        cases.add(Arguments.of("net", "", null));
        cases.add(Arguments.of("net", "com", null));
        cases.add(Arguments.of("net", "x.com", null));
        cases.add(Arguments.of("a.net", "net", null));
        cases.add(Arguments.of("a.net", "x.net", null));
        cases.add(Arguments.of("a.net", "aa.net", null));
        cases.add(Arguments.of("a.net", "a..net", null));
        cases.add(Arguments.of("*.net", "net", null));
        cases.add(Arguments.of("*.net", "x.com", null));
        cases.add(Arguments.of("*.net", "..com", null));
        cases.add(Arguments.of("*.net", "x..com", null));
        cases.add(Arguments.of("a.*.net", "aa.x.net", null));

        cases.add(Arguments.of("net", "Net", "Net"));
        cases.add(Arguments.of("net", "net", "net"));
        cases.add(Arguments.of("net", "x.net", "net"));
        cases.add(Arguments.of("a.net", "a.net", "a.net"));
        cases.add(Arguments.of("a.net", "x.a.net", "a.net"));
        cases.add(Arguments.of("*.net", "x.net", "x.net"));
        cases.add(Arguments.of("*.net", "x.x.net", "x.net"));
        cases.add(Arguments.of("a.*.net", "a.x.net", "a.x.net"));
        cases.add(Arguments.of("a.*.net", "x.a.x.net", "a.x.net"));
        cases.add(Arguments.of("a.*.b.*.c", "a.x.b.x.c", "a.x.b.x.c"));
        cases.add(Arguments.of("a.*.b.*.c", "x.a.x.b.x.c", "a.x.b.x.c"));

        return cases;
    }

    @ParameterizedTest
    @MethodSource("CASES")
    public void testMatch(String rule, String domain, String match) {
        RuleMatcher matcher = new RuleMatcher(rule);
        assertEquals(match, matcher.match(domain));
    }
}
