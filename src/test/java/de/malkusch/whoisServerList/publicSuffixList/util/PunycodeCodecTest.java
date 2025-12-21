package de.malkusch.whoisServerList.publicSuffixList.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PunycodeCodecTest {

    @Test
    public void testDecode() {
        assertEquals("食狮.com.cn", new PunycodeAutoDecoder().decode("食狮.com.cn"));
        assertEquals("食狮.com.cn", new PunycodeAutoDecoder().decode("xn--85x722f.com.cn"));
        assertEquals("example.net", new PunycodeAutoDecoder().decode("example.net"));
    }

    @Test
    public void testRecode() {
        {
            String domain = "食狮.com.cn";
            PunycodeAutoDecoder decoder = new PunycodeAutoDecoder();
            decoder.decode(domain);
            assertEquals(domain, decoder.recode(domain));
        }
        {
            String domain = "xn--85x722f.com.cn";
            PunycodeAutoDecoder decoder = new PunycodeAutoDecoder();
            decoder.decode(domain);
            assertEquals(domain, decoder.recode(domain));
        }
        {
            String domain = "example.net";
            PunycodeAutoDecoder decoder = new PunycodeAutoDecoder();
            decoder.decode(domain);
            assertEquals(domain, decoder.recode(domain));
        }
    }
}
