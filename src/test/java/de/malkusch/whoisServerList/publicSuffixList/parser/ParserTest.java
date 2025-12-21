package de.malkusch.whoisServerList.publicSuffixList.parser;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;
import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class ParserTest {

    @Test
    public void testParse() throws IOException {
        Parser parser = new Parser();
        String[] expected = {"ac", "com.ac", "edu.ac", "*.er", "hk", "教育.hk", "敎育.hk", "政府.hk",
                "za.net", "za.org"};

        PublicSuffixList psl = new PublicSuffixListFactory().build();
        InputStream stream = getClass().getResourceAsStream("/test.dat");
        List<Rule> rules = parser.parse(stream, psl.getCharset());
        for (int i = 0; i < rules.size(); i++) {
            assertEquals(expected[i], rules.get(i).toString());

        }
    }

    @Test
    public void testParseLine() {
        Parser parser = new Parser();

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
