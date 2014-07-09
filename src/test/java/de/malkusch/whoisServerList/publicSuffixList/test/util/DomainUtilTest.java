package de.malkusch.whoisServerList.publicSuffixList.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

public class DomainUtilTest {

	@Test
	public void testNormalize() {
		assertEquals("example.net", DomainUtil.normalize("Example.net"));
		assertEquals("example.net", DomainUtil.normalize("example.net"));
		assertEquals("example.قطر", DomainUtil.normalize("example.قطر"));
		assertEquals("example.قطر", DomainUtil.normalize("Example.قطر"));
	}

}
