package de.malkusch.whoisServerList.publicSuffixList;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
}
