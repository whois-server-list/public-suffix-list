package de.malkusch.whoisServerList.publicSuffixList.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixList;
import de.malkusch.whoisServerList.publicSuffixList.PublicSuffixListFactory;
import de.malkusch.whoisServerList.publicSuffixList.util.NoopConsumer;
import de.malkusch.whoisServerList.publicSuffixList.util.TestUtil;

@RunWith(Parameterized.class)
public class GetRegistrableDomainTest {

    @Rule
    public TestRule benchmark = new BenchmarkRule(NoopConsumer.INSTANCE);

    @Parameter
    public PublicSuffixList psl;

    @Parameters
    static public Collection<PublicSuffixList[]> getTestLists() throws IOException, ClassNotFoundException {
        List<PublicSuffixList[]> cases = new ArrayList<>();

        for (Properties properties : PublicSuffixListTest.getPropertyCases()) {
            cases.add(new PublicSuffixList[]{
                    new PublicSuffixListFactory().build(properties)});

        }
        return cases;
    }

    @BenchmarkOptions(
            benchmarkRounds = 10000,
            concurrency = BenchmarkOptions.CONCURRENCY_AVAILABLE_CORES)
    @Test
    public void testGetRegistrableDomain() throws IOException {
        for (String[] tupel : TestUtil.getCheckPublicSuffixCases()) {
            String domain = tupel[0];
            String registrableDomain = tupel[1];

            String actual = StringUtils.lowerCase(psl.getRegistrableDomain(domain));
            String expected = StringUtils.lowerCase(registrableDomain);
            assertEquals(
                String.format("%s should return %s, but was %s", domain, expected, actual),
                expected,
                actual
            );
        }
    }

}