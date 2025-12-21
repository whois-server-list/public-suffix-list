package de.malkusch.whoisServerList.publicSuffixList.rule;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LabelMatcherTest {


    public static Collection<Arguments> CASES() {
        // pattern, label, match
        ArrayList<Arguments> cases = new ArrayList<>();

        cases.add(Arguments.of("net", "", false));
        cases.add(Arguments.of("net", null, false));
        cases.add(Arguments.of("*", "", false));
        cases.add(Arguments.of("*", null, false));
        cases.add(Arguments.of("net", "com", false));
        cases.add(Arguments.of("net", "xnet", false));

        cases.add(Arguments.of("net", "net", true));
        cases.add(Arguments.of("net", "Net", true));
        cases.add(Arguments.of("*", "x", true));
        cases.add(Arguments.of("*", "X", true));

        return cases;
    }

    @ParameterizedTest
    @MethodSource("CASES")
    public void voidMatch(String pattern, String label, boolean match) {
        LabelMatcher matcher = new LabelMatcher(pattern);
        assertEquals(match, matcher.isMatch(label));
    }
}
