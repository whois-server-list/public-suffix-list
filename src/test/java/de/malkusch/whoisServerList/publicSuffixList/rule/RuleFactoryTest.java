package de.malkusch.whoisServerList.publicSuffixList.rule;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RuleFactoryTest {

    static public List<Rule> RULES() {
        List<Rule> cases = new ArrayList<>();

        cases.add(new Rule("de"));
        cases.add(new Rule("co.uk"));
        cases.add(new Rule("*.ck"));

        cases.add(new Rule("www.ck", true));

        return cases;
    }

    @ParameterizedTest
    @MethodSource("RULES")
    public void testBuildRule(Rule expected) {
        RuleFactory factory = new RuleFactory();
        assertEquals(expected, factory.build(expected.toString()));
    }

}
