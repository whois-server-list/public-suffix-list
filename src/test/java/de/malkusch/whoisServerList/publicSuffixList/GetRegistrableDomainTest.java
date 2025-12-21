package de.malkusch.whoisServerList.publicSuffixList;

import de.malkusch.whoisServerList.publicSuffixList.test.util.TestUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GetRegistrableDomainTest {


    public static Collection<PublicSuffixList> PSL() throws IOException, ClassNotFoundException {
        return PublicSuffixListTest.PSL();
    }

    @ParameterizedTest
    @MethodSource("PSL")
    public void testGetRegistrableDomain(PublicSuffixList psl) throws IOException {
        for (String[] tupel : TestUtil.getCheckPublicSuffixCases()) {
            String domain = tupel[0];
            String registrableDomain = tupel[1];

            String actual = StringUtils.lowerCase(psl.getRegistrableDomain(domain));
            String expected = StringUtils.lowerCase(registrableDomain);
            assertEquals(
                    expected,
                    actual,
                    String.format("%s should return %s, but was %s", domain, expected, actual)
            );
        }
    }
}