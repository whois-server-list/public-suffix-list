package de.malkusch.whoisServerList.publicSuffixList.test.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.ListIndex;
import de.malkusch.whoisServerList.publicSuffixList.index.tree.TreeIndex;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleFactory;
import de.malkusch.whoisServerList.publicSuffixList.util.TestUtil;

@RunWith(Parameterized.class)
public class IndexTest {

	@Parameter
	public Index index;
	
	@Parameters
	public static Collection<Index[]> getIndexes() {
		Collection<Index[]> cases = new ArrayList<>();
		
		cases.add(new Index[]{ new ListIndex() });
		cases.add(new Index[]{ new TreeIndex() });
		
		return cases;
	}
	
	@Test
	public void testGetRules() throws IOException {
		List<Rule> rules = TestUtil.getDefaultParsedRules();
		Set<Rule> expected = new HashSet<>(rules);
		
		index.setRules(rules);
		Set<Rule> actual = new HashSet<>(index.getRules());
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIndex() {
		List<Rule> rules = convert(new String[]{
				"net",
				"com",
				"a.com",
				"a.org",
				"org",
				"*.ck",
				"!www.ck",
		});
		index.setRules(rules);
		
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
	
	private List<Rule> convert(String[] rules) {
		RuleFactory factory = new RuleFactory();
		List<Rule> list = new ArrayList<>();
		for (String rule : rules) {
			list.add(factory.build(rule));
			
		}
		return list;
	}
	
}
