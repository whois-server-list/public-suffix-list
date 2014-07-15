package de.malkusch.whoisServerList.publicSuffixList;

import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;
import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;

@RunWith(Parameterized.class)
public class FailGetRegistrableDomainTest {

    private PublicSuffixList psl;

    @Before
    public void setPSL() throws IOException {
        psl = new PublicSuffixListFactory().build();
    }

    @Parameters
    static public Iterable<String[]> getPublicSuffixes() {
        return Arrays.asList(new String[][]{
                {"co.uk"},
                {"test.sch.uk"},
                {"test.ck"},
                {"ck"},
                {"invalid"}
        });
    }

    @Parameter
    public String domain;

    @Test
    public void testFailGetRegistrableDomain() {
        assertNull(psl.getRegistrableDomain(domain));
    }

}