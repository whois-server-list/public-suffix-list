package de.malkusch.whoisServerList.publicSuffixList.test.rule;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.rule.RuleMatcher;

@RunWith(Parameterized.class)
public class RuleMatcherTest {
	
	@Parameter(0)
	public String rule;
	
	@Parameter(1)
	public String domain;
	
	@Parameter(2)
	public String match;
	
	@Parameters
	public static Collection<String[]> provideCases() {
		ArrayList<String[]> cases = new ArrayList<>();
		
		cases.add(new String[]{"net", null, null});
		cases.add(new String[]{"net", "", null});
		cases.add(new String[]{"net", "com", null});
		cases.add(new String[]{"net", "x.com", null});
		cases.add(new String[]{"a.net", "net", null});
		cases.add(new String[]{"a.net", "x.net", null});
		cases.add(new String[]{"a.net", "aa.net", null});
		cases.add(new String[]{"a.net", "a..net", null});
		cases.add(new String[]{"*.net", "net", null});
		cases.add(new String[]{"*.net", "x.com", null});
		cases.add(new String[]{"*.net", "..com", null});
		cases.add(new String[]{"*.net", "x..com", null});
		cases.add(new String[]{"a.*.net", "aa.x.net", null});
		
		cases.add(new String[]{"net", "Net", "Net"});
		cases.add(new String[]{"net", "net", "net"});
		cases.add(new String[]{"net", "x.net", "net"});
		cases.add(new String[]{"a.net", "a.net", "a.net"});
		cases.add(new String[]{"a.net", "x.a.net", "a.net"});
		cases.add(new String[]{"*.net", "x.net", "x.net"});
		cases.add(new String[]{"*.net", "x.x.net", "x.net"});
		cases.add(new String[]{"a.*.net", "a.x.net", "a.x.net"});
		cases.add(new String[]{"a.*.net", "x.a.x.net", "a.x.net"});
		cases.add(new String[]{"a.*.b.*.c", "a.x.b.x.c", "a.x.b.x.c"});
		cases.add(new String[]{"a.*.b.*.c", "x.a.x.b.x.c", "a.x.b.x.c"});
		
		return cases;
	}
	
	@Test
	public void testMatch() {
		RuleMatcher matcher = new RuleMatcher(rule);
		assertEquals(match, matcher.match(domain));
	}

}
