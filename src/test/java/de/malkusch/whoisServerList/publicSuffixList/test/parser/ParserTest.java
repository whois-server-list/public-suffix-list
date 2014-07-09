package de.malkusch.whoisServerList.publicSuffixList.test.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

public class ParserTest {
	
	private Parser parser;
	
	@Before
	public void setParser() {
		parser = new Parser();
	}
	
	@Test
	public void testParse() throws IOException {
		String[] expected = {"ac", "com.ac", "edu.ac", "*.er", "hk", "教育.hk", "敎育.hk", "政府.hk",
				"za.net", "za.org"};

		InputStream stream = getClass().getResourceAsStream("/test.dat");
		List<Rule> rules = parser.parse(stream);
		for (int i = 0; i < rules.size(); i++) {
			assertEquals(expected[i], rules.get(i).toString());
			
		}
	}

	@Test
	public void testParseLine() {
		assertNull(parser.parseLine(null));
		assertNull(parser.parseLine(""));
		assertNull(parser.parseLine("\n"));
		assertNull(parser.parseLine(" "));
		assertNull(parser.parseLine(" \n"));
		assertNull(parser.parseLine("//"));
		assertNull(parser.parseLine("//\n"));
		assertNull(parser.parseLine("//comment"));
		assertNull(parser.parseLine("//comment\n"));
		assertNull(parser.parseLine("// comment"));
		assertNull(parser.parseLine("// comment\n"));
		
		assertEquals("/", parser.parseLine("/").toString());
		assertEquals("/", parser.parseLine("/\n").toString());
		assertEquals("de", parser.parseLine("de").toString());
		assertEquals("de", parser.parseLine("de comment").toString());
		assertEquals("de", parser.parseLine("de\n").toString());
		assertEquals("de", parser.parseLine(" de ").toString());
		assertEquals("co.uk", parser.parseLine("co.uk").toString());
		assertEquals("*.de", parser.parseLine("*.de").toString());
		assertEquals("!de", parser.parseLine("!de").toString());
	}
	
}
