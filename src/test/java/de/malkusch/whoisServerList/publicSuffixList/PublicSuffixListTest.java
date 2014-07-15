package de.malkusch.whoisServerList.publicSuffixList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;
import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.index.array.ArrayIndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.index.tree.TreeIndexFactory;

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
        listIndex.setProperty(
            PublicSuffixListFactory.PROPERTY_INDEX_FACTORY,
            ArrayIndexFactory.class.getName());
        cases.add(listIndex);

        Properties treeIndex = new Properties(defaultProperties);
        listIndex.setProperty(
            PublicSuffixListFactory.PROPERTY_INDEX_FACTORY,
            TreeIndexFactory.class.getName());
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
    public void testGetRegistrableDomain() {
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

    @Test
    public void testIsRegistrable() {
        assertTrue(psl.isRegistrable("example.de"));
        assertTrue(psl.isRegistrable("example.uk"));
        assertTrue(psl.isRegistrable("example.co.uk"));
        assertTrue(psl.isRegistrable("example.test.sch.uk"));
        assertTrue(psl.isRegistrable("example.test.ck"));
        assertTrue(psl.isRegistrable("www.ck"));
        assertTrue(psl.isRegistrable("example.invalid"));
        assertTrue(psl.isRegistrable("www.global.prod.fastly.net"));

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
