package de.malkusch.whoisServerList.publicSuffixList.test.rule;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleFactory;

@RunWith(Parameterized.class)
public class RuleFactoryTest {

    @Parameter
    public Rule expected;

    @Parameters
    static public List<Rule[]> provideTestCases() {
        List<Rule[]> cases = new ArrayList<>();

        cases.add(new Rule[]{new Rule("de")});
        cases.add(new Rule[]{new Rule("co.uk")});
        cases.add(new Rule[]{new Rule("*.ck")});

        cases.add(new Rule[]{new Rule("www.ck", true)});

        return cases;
    }

    @Test
    public void testBuildRule() {
        RuleFactory factory = new RuleFactory();
        assertEquals(expected, factory.build(expected.toString()));
    }

}
