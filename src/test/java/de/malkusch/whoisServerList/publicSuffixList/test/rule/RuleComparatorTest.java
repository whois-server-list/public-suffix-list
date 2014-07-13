package de.malkusch.whoisServerList.publicSuffixList.test.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleComparator;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleFactory;

@RunWith(Parameterized.class)
public class RuleComparatorTest {
	
	@Parameter(0)
	public String prevailingRule;
	
	@Parameter(1)
	public String rule;
	
	@Parameters
	public static Collection<String[]> getCases() {
		Collection<String[]> cases = new ArrayList<>();
		cases.add(new String[] {"a.net", "net"});
		cases.add(new String[] {"a.b.net", "net"});
		cases.add(new String[] {"a.b.net", "a.net"});
		cases.add(new String[] {"!net", "a.net"});
		cases.add(new String[] {"!net", "a.b.net"});
		return cases;
	}
	
	@Test
	public void testCompare() {
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
