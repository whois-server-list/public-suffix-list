package de.malkusch.whoisServerList.publicSuffixList.rule;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RuleComparatorTest {

    public static Collection<Arguments> RULES() {
        Collection<Arguments> cases = new ArrayList<>();
        cases.add(Arguments.of("a.net", "net"));
        cases.add(Arguments.of("a.b.net", "net"));
        cases.add(Arguments.of("a.b.net", "a.net"));
        cases.add(Arguments.of("!net", "a.net"));
        cases.add(Arguments.of("!net", "a.b.net"));
        return cases;
    }

    @ParameterizedTest
    @MethodSource("RULES")
    public void testCompare(String prevailingRule, String rule) {
        Comparator<Rule> comparator = new RuleComparator();

        RuleFactory factory = new RuleFactory();
        Rule rule1 = factory.build(prevailingRule);
        Rule rule2 = factory.build(rule);

        assertEquals(1, comparator.compare(rule1, rule2));
        assertEquals(-1, comparator.compare(rule2, rule1));
        assertEquals(0, comparator.compare(rule1, rule1));
        assertEquals(0, comparator.compare(rule2, rule2));
    }
}
