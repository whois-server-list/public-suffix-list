package de.malkusch.whoisServerList.publicSuffixList.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

public class DomainUtilTest {
	
	@Test
	public void testJoinLabels() {
		assertNull(DomainUtil.joinLabels((String[]) null));
		
		assertEquals("", DomainUtil.joinLabels(new String[]{}));
		assertEquals("net", DomainUtil.joinLabels(new String[]{"net"}));
		assertEquals("example.net", DomainUtil.joinLabels(new String[]{"example", "net"}));
		assertEquals("www.example.net", DomainUtil.joinLabels(new String[]{"www", "example", "net"}));
		assertEquals("example.قطر", DomainUtil.joinLabels(new String[]{"example", "قطر"}));
	}
	
	@Test
	public void testSplitLabels() {
		assertNull(DomainUtil.splitLabels(null));
		
		assertArrayEquals(new String[]{}, DomainUtil.splitLabels(""));
		assertArrayEquals(new String[]{"net"}, DomainUtil.splitLabels("net"));
		assertArrayEquals(new String[]{"example", "net"}, DomainUtil.splitLabels("example.net"));
		assertArrayEquals(new String[]{"www", "example", "net"}, DomainUtil.splitLabels("www.example.net"));
		assertArrayEquals(new String[]{"example", "قطر"}, DomainUtil.splitLabels("example.قطر"));
	}

}
