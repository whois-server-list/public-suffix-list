package de.malkusch.whoisServerList.publicSuffixList;

import de.malkusch.whoisServerList.publicSuffixList.index.array.ArrayIndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.index.tree.TreeIndexFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class PublicSuffixListTest {

    public static Collection<PublicSuffixList> PSL() throws IOException, ClassNotFoundException {
        Collection<PublicSuffixList> cases = new ArrayList<>();

        Properties defaultProperties = new Properties();
        defaultProperties.load(PublicSuffixListTest.class.getResourceAsStream(PublicSuffixListFactory.PROPERTY_FILE));
        cases.add(new PublicSuffixListFactory().build(defaultProperties));

        Properties listIndex = new Properties(defaultProperties);
        listIndex.setProperty(
                PublicSuffixListFactory.PROPERTY_INDEX_FACTORY,
                ArrayIndexFactory.class.getName());
        cases.add(new PublicSuffixListFactory().build(listIndex));

        Properties treeIndex = new Properties(defaultProperties);
        listIndex.setProperty(
                PublicSuffixListFactory.PROPERTY_INDEX_FACTORY,
                TreeIndexFactory.class.getName());
        cases.add(new PublicSuffixListFactory().build(treeIndex));

        return cases;
    }

    @ParameterizedTest
    @MethodSource("PSL")
    public void testGetURL(PublicSuffixList psl) throws MalformedURLException {
        assertEquals(new URL("https://publicsuffix.org/list/effective_tld_names.dat"), psl.getURL());
    }

    @ParameterizedTest
    @MethodSource("PSL")
    public void testGetRegistrableDomain(PublicSuffixList psl) {
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

    @ParameterizedTest
    @MethodSource("PSL")
    public void testIsRegistrable(PublicSuffixList psl) {
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

    @ParameterizedTest
    @MethodSource("PSL")
    public void testGetPublicSuffix(PublicSuffixList psl) {
        assertNull(psl.getPublicSuffix(""));
        assertNull(psl.getPublicSuffix(null));

        assertEquals("com", psl.getPublicSuffix("example.com"));
        assertEquals("com", psl.getPublicSuffix("www.example.com"));
        assertEquals("co.uk", psl.getPublicSuffix("www.example.co.uk"));
        assertEquals("uk", psl.getPublicSuffix("example.uk"));
        assertEquals("test.sch.uk", psl.getPublicSuffix("example.test.sch.uk"));
        assertEquals("fallback", psl.getPublicSuffix("example.fallback"));
        assertEquals("公司.cn", psl.getPublicSuffix("example.公司.cn"));
        assertEquals("公司.cn", psl.getPublicSuffix("食狮.公司.cn"));
        assertEquals("xn--55qx5d.cn", psl.getPublicSuffix("example.xn--85x722f.xn--55qx5d.cn"));
    }

    @ParameterizedTest
    @MethodSource("PSL")
    public void testIsPublicSuffix(PublicSuffixList psl) {
        assertTrue(psl.isPublicSuffix("de"));
        assertTrue(psl.isPublicSuffix("de"));
        assertTrue(psl.isPublicSuffix("co.uk"));
        assertTrue(psl.isPublicSuffix("教育.hk"));
        assertTrue(psl.isPublicSuffix("xn--55qx5d.cn"));
        assertTrue(psl.isPublicSuffix("uk"));
        assertTrue(psl.isPublicSuffix("test.sch.uk"));

        assertFalse(psl.isPublicSuffix("example.net"));
        assertFalse(psl.isPublicSuffix("markus.malkusch.de"));
        assertFalse(psl.isPublicSuffix("www.xn--55qx5d.cn"));
        assertFalse(psl.isPublicSuffix("www.公司.cn"));
    }
}