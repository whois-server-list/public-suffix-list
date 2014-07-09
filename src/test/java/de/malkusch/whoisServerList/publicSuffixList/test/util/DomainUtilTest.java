package de.malkusch.whoisServerList.publicSuffixList.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

public class DomainUtilTest {
	
	@Test
	public void testSplit() {
		assertNull(DomainUtil.split(null));
		
		assertArrayEquals(new String[]{}, DomainUtil.split(""));
		assertArrayEquals(new String[]{"net"}, DomainUtil.split("net"));
		assertArrayEquals(new String[]{"example", "net"}, DomainUtil.split("example.net"));
		assertArrayEquals(new String[]{"www", "example", "net"}, DomainUtil.split("www.example.net"));
		assertArrayEquals(new String[]{"example", "قطر"}, DomainUtil.split("example.قطر"));
	}

	@Test
	public void testNormalize() {
		assertNull(DomainUtil.normalize(null));
		
		assertEquals("example.net", DomainUtil.normalize("Example.net"));
		assertEquals("example.net", DomainUtil.normalize("example.net"));
		assertEquals("example.قطر", DomainUtil.normalize("example.قطر"));
		assertEquals("example.قطر", DomainUtil.normalize("Example.قطر"));
	}

}
