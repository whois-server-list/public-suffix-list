package de.malkusch.whoisServerList.publicSuffixList.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;
import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.exception.SuffixDomainException;
import de.malkusch.whoisServerList.publicSuffixList.index.ListIndex;
import de.malkusch.whoisServerList.publicSuffixList.index.tree.TreeIndex;

@RunWith(Parameterized.class)
public class PublicSuffixListTest {
	
	private PublicSuffixList psl;
	
	@Parameter
	public Properties properties;
	
	@Parameters
	public static Collection<Properties[]> getProperties() throws IOException {
		Collection<Properties[]> cases = new ArrayList<>();
		
		for (Properties properties : getPropertyCases()) {
			cases.add(new Properties[]{properties});
			
		}
		
		return cases;
	}
	
	static public Collection<Properties> getPropertyCases() throws IOException {
		Collection<Properties> cases = new ArrayList<>();
		
		Properties defaultProperties = new Properties();
		defaultProperties.load(PublicSuffixListTest.class.getResourceAsStream(PublicSuffixListFactory.PROPERTY_FILE));
		cases.add(defaultProperties);
		
		Properties listIndex = new Properties(defaultProperties);
		listIndex.setProperty(PublicSuffixListFactory.PROPERTY_INDEX, ListIndex.class.getName());
		cases.add(listIndex);
		
		Properties treeIndex = new Properties(defaultProperties);
		listIndex.setProperty(PublicSuffixListFactory.PROPERTY_INDEX, TreeIndex.class.getName());
		cases.add(treeIndex);
		
		return cases;
	}
	
	@Before
	public void setPSL() throws IOException, ClassNotFoundException {
		psl = new PublicSuffixListFactory().build(properties);
	}
	
	@Test
	public void testGetURL() throws MalformedURLException {
		assertEquals(new URL("https://publicsuffix.org/list/effective_tld_names.dat"), psl.getURL());
	}
	
	@Test
	public void testGetRegistrableDomain() throws SuffixDomainException {
		assertNull(psl.getRegistrableDomain(null));
		assertNull(psl.getRegistrableDomain(""));
		assertNull(psl.getRegistrableDomain(".net"));
		
		assertEquals("example.invalid", psl.getRegistrableDomain("example.invalid"));
		assertEquals("example.de", psl.getRegistrableDomain("example.de"));
		assertEquals("example.de", psl.getRegistrableDomain("www.example.de"));
		assertEquals("example.co.uk", psl.getRegistrableDomain("example.co.uk"));
		assertEquals("example.test.sch.uk", psl.getRegistrableDomain("example.test.sch.uk"));
		assertEquals("www.ck", psl.getRegistrableDomain("www.ck"));
		assertEquals("example.de", psl.getRegistrableDomain("www.example.de"));
		assertEquals("example.test.sch.uk", psl.getRegistrableDomain("www.example.test.sch.uk"));
		assertEquals("example.test.ck", psl.getRegistrableDomain("www.example.test.ck"));
	}
	
	@RunWith(Parameterized.class)
	public static class TestGetRegistrableDomain {
		
		@Parameter(0)
		public String domain;
		
		@Parameter(1)
		public String registrableDomain;
		
		@Parameter(2)
		public Properties properties;
		
		@Parameters
		static public Collection<Object[]> readMozillasTestCases() throws IOException {
			List<Object[]> cases = new ArrayList<>();
			
			InputStream stream = TestGetRegistrableDomain.class.getResourceAsStream("/checkPublicSuffix.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			
			Pattern pattern = Pattern.compile("^checkPublicSuffix\\((\\S+),\\s*(\\S+)\\);\\s*$");
			
			String line;
			while((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (! matcher.matches()) {
					continue;
					
				}
				String domain = parseArgument(matcher.group(1));
				String registrableDomain = parseArgument(matcher.group(2));
				
				for (Properties properties : getPropertyCases()) {
					cases.add(new Object[]{ domain, registrableDomain, properties });
					
				}
			}
			
			return cases;
		}
		
		static private String parseArgument(String argument) {
			if (argument.equals("null")) {
				return null;
				
			}
			Pattern pattern = Pattern.compile("'(.+)'");
			Matcher matcher = pattern.matcher(argument);
			assertTrue(matcher.matches());
			
			return matcher.group(1);
		}
		
		@Test
		public void testGetRegistrableDomain() throws IOException, ClassNotFoundException {
			PublicSuffixList psl = new PublicSuffixListFactory().build(properties);
			
			String actual = StringUtils.lowerCase(psl.getRegistrableDomain(domain));
			String expected = StringUtils.lowerCase(registrableDomain);
			assertEquals(
				String.format("%s should return %s, but was %s", domain, expected, actual),
				expected,
				actual
			);
		}
		
	}
	
	@RunWith(Parameterized.class)
	static public class FailGetRegistrableDomainTest {
		
		private PublicSuffixList psl;
		
		@Before
		public void setPSL() throws IOException {
			psl = new PublicSuffixListFactory().build();
		}
		
		@Parameters
		static public Iterable<String[]> getPublicSuffixes() {
			return Arrays.asList(new String[][]{
					{"co.uk"},
					{"test.sch.uk"},
					{"test.ck"},
					{"ck"},
					{"invalid"}
			});
		}
		
		@Parameter
		public String domain;
		
		@Test(expected = SuffixDomainException.class)
		public void testFailGetRegistrableDomain() throws SuffixDomainException {
			psl.getRegistrableDomain(domain);
		}
		
	}
	
	@Test
	public void testIsRegistrable() {
		assertTrue(psl.isRegistrable("example.de"));
		assertTrue(psl.isRegistrable("example.uk"));
		assertTrue(psl.isRegistrable("example.co.uk"));
		assertTrue(psl.isRegistrable("example.test.sch.uk"));
		assertTrue(psl.isRegistrable("example.test.ck"));
		assertTrue(psl.isRegistrable("www.ck"));
		assertTrue(psl.isRegistrable("example.invalid"));
		
		assertFalse(psl.isRegistrable("www.example.de"));
		assertFalse(psl.isRegistrable("co.uk"));
		assertFalse(psl.isRegistrable("test.sch.uk"));
		assertFalse(psl.isRegistrable("www.example.test.sch.uk"));
		assertFalse(psl.isRegistrable("www.example.test.ck"));
		assertFalse(psl.isRegistrable("test.ck"));
		assertFalse(psl.isRegistrable("ck"));
		assertFalse(psl.isRegistrable("invalid"));
	}
	
	@Test
	public void testGetPublicSuffix() {
		assertNull(psl.getPublicSuffix(""));
		assertNull(psl.getPublicSuffix(null));
		
		assertEquals("com", psl.getPublicSuffix("example.com"));
		assertEquals("com", psl.getPublicSuffix("www.example.com"));
		assertEquals("co.uk", psl.getPublicSuffix("www.example.co.uk"));
		assertEquals("uk", psl.getPublicSuffix("example.uk"));
		assertEquals("test.sch.uk", psl.getPublicSuffix("example.test.sch.uk"));
		assertEquals("fallback", psl.getPublicSuffix("example.fallback"));
	}
	
	@Test
	public void testIsPublicSuffix() {
		assertTrue(psl.isPublicSuffix("de"));
		assertTrue(psl.isPublicSuffix("de"));
		assertTrue(psl.isPublicSuffix("co.uk"));
		assertTrue(psl.isPublicSuffix("教育.hk"));
		assertTrue(psl.isPublicSuffix("uk"));
		assertTrue(psl.isPublicSuffix("test.sch.uk"));
		
		assertFalse(psl.isPublicSuffix("example.net"));
		assertFalse(psl.isPublicSuffix("markus.malkusch.de"));
	}

}
