package de.malkusch.whoisServerList.publicSuffixList;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNull;


public class FailGetRegistrableDomainTest {

    @ParameterizedTest
    @ValueSource(strings = {"co.uk",
            "test.sch.uk",
            "test.ck",
            "ck",
            "invalid"})
    public void testFailGetRegistrableDomain(String domain) {
        PublicSuffixList psl = new PublicSuffixListFactory().build();
        assertNull(psl.getRegistrableDomain(domain));
    }
}