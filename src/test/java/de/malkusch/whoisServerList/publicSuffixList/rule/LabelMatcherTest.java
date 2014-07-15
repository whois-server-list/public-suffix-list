package de.malkusch.whoisServerList.publicSuffixList.rule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.rule.LabelMatcher;

@RunWith(Parameterized.class)
public class LabelMatcherTest {

    @Parameter(0)
    public String pattern;

    @Parameter(1)
    public String label;

    @Parameter(2)
    public boolean match;

    @Parameters
    public static Collection<Object[]> getCases() {
        // pattern, label, match
        ArrayList<Object[]> cases = new ArrayList<>();

        cases.add(new Object[]{"net", "", false});
        cases.add(new Object[]{"net", null, false});
        cases.add(new Object[]{"*", "", false});
        cases.add(new Object[]{"*", null, false});
        cases.add(new Object[]{"net", "com", false});
        cases.add(new Object[]{"net", "xnet", false});

        cases.add(new Object[]{"net", "net", true});
        cases.add(new Object[]{"net", "Net", true});
        cases.add(new Object[]{"*", "x", true});
        cases.add(new Object[]{"*", "X", true});

        return cases;
    }

    @Test
    public void voidMatch() {
        LabelMatcher matcher = new LabelMatcher(pattern);
        assertEquals(match, matcher.isMatch(label));
    }

}
