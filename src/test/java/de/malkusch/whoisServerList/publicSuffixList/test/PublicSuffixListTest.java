package de.malkusch.whoisServerList.publicSuffixList.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;

public class PublicSuffixListTest {
	
	private PublicSuffixList psl;
	
	@Before
	public void setPSL() throws IOException {
		psl = new PublicSuffixList();
	}
	
	@Test
	public void testGetURL() throws MalformedURLException {
		assertEquals(new URL("https://publicsuffix.org/list/effective_tld_names.dat"), psl.getURL());
	}

}
