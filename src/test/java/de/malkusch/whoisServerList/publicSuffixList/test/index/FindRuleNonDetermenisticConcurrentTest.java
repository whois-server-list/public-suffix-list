package de.malkusch.whoisServerList.publicSuffixList.test.index;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.util.NoopConsumer;
import de.malkusch.whoisServerList.publicSuffixList.util.TestUtil;

@RunWith(Parameterized.class)
public class FindRuleNonDetermenisticConcurrentTest {

    @Rule
    public TestRule benchmarkRun = new BenchmarkRule(NoopConsumer.INSTANCE);

    @Parameter
    public Index index;

    @Parameters(name = "{0}")
    public static Collection<Index[]> getIndexes() throws IOException {
        return TestUtil.getTestIndexes();
    }

    @BenchmarkOptions(
            benchmarkRounds = 5000,
            concurrency = BenchmarkOptions.CONCURRENCY_AVAILABLE_CORES)
    @Test
    public void benchmarkFindRule() throws Exception {
        assertEquals(
                "global.prod.fastly.net",
                index.findRule("www.global.prod.fastly.net").getPattern());
    }

}
