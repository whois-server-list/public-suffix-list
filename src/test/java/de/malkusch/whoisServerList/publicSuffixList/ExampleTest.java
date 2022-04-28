package de.malkusch.whoisServerList.publicSuffixList;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;
import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;

public class ExampleTest {

    @Test
    public void testExample() throws IOException {
        PublicSuffixListFactory factory = new PublicSuffixListFactory();
        PublicSuffixList suffixList = factory.build();

        assertTrue(suffixList.isPublicSuffix("net"));
        assertFalse(suffixList.isPublicSuffix("example.net"));

        assertEquals("net", suffixList.getPublicSuffix("www.example.net"));
        assertEquals("net", suffixList.getPublicSuffix("net"));

        assertTrue(suffixList.isRegistrable("example.net"));
        assertFalse(suffixList.isRegistrable("www.example.net"));
        assertFalse(suffixList.isRegistrable("net"));

        assertNull(suffixList.getRegistrableDomain("net"));
        assertEquals("example.net", suffixList.getRegistrableDomain("example.net"));
        assertEquals("example.net", suffixList.getRegistrableDomain("www.example.net"));
        assertEquals("example.co.uk", suffixList.getRegistrableDomain("example.co.uk"));
        assertEquals("example.co.uk", suffixList.getRegistrableDomain("www.example.co.uk"));

        assertEquals("食狮.com.cn", suffixList.getRegistrableDomain("食狮.com.cn"));
        assertEquals("xn--85x722f.com.cn", suffixList.getRegistrableDomain("xn--85x722f.com.cn"));
    }

    @Test
    public void testExampleWithoutWildcardRule() throws IOException, ClassNotFoundException {
        PublicSuffixListFactory factory = new PublicSuffixListFactory();
        Properties properties = factory.getDefaults();
        properties.setProperty("psl.use.wildcard", "false");
        PublicSuffixList suffixList = factory.build(properties);

        assertTrue(suffixList.isPublicSuffix("net"));
        assertFalse(suffixList.isPublicSuffix("bad"));

        assertNull(suffixList.getRegistrableDomain("net"));
        assertNull(suffixList.getRegistrableDomain("example.example"));
        assertNull(suffixList.getRegistrableDomain("bad.bad"));
        assertEquals("example.net", suffixList.getRegistrableDomain("www.example.net"));
        assertEquals("example.co.uk", suffixList.getRegistrableDomain("example.co.uk"));
        assertEquals("example.co.uk", suffixList.getRegistrableDomain("www.example.co.uk"));
    }

}
