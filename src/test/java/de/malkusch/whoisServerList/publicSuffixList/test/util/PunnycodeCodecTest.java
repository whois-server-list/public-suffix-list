package de.malkusch.whoisServerList.publicSuffixList.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import de.malkusch.whoisServerList.publicSuffixList.util.PunnycodeAutoDecoder;

public class PunnycodeCodecTest {
	
	@Test
	public void testDecode() {
		assertEquals("食狮.com.cn", new PunnycodeAutoDecoder().decode("食狮.com.cn"));
		assertEquals("食狮.com.cn", new PunnycodeAutoDecoder().decode("xn--85x722f.com.cn"));
		assertEquals("example.net", new PunnycodeAutoDecoder().decode("example.net"));
	}
	
	@Test
	public void testRecode() {
		{
			String domain = "食狮.com.cn";
			PunnycodeAutoDecoder decoder = new PunnycodeAutoDecoder();
			decoder.decode(domain);
			assertEquals(domain, decoder.recode(domain));
		}
		{
			String domain = "xn--85x722f.com.cn";
			PunnycodeAutoDecoder decoder = new PunnycodeAutoDecoder();
			decoder.decode(domain);
			assertEquals(domain, decoder.recode(domain));
		}
		{
			String domain = "example.net";
			PunnycodeAutoDecoder decoder = new PunnycodeAutoDecoder();
			decoder.decode(domain);
			assertEquals(domain, decoder.recode(domain));
		}
	}

}
