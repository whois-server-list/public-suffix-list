package de.malkusch.whoisServerList.publicSuffixList.benchmark.index;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang3.RandomStringUtils;
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
import de.malkusch.whoisServerList.publicSuffixList.util.TestUtil;

@RunWith(Parameterized.class)
public class IndexBenchmark {

    @Parameter
    public Index index;

    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();

    @Parameters(name = "{0}")
    public static Collection<Index[]> getIndexes() throws IOException {
        return TestUtil.getTestIndexes();
    }

    @BenchmarkOptions(
            benchmarkRounds = 20000,
            warmupRounds = 2000,
            concurrency = BenchmarkOptions.CONCURRENCY_AVAILABLE_CORES)
    @Test
    public void benchmarkFindRule() throws Exception {
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
        index.findRule("www.global.prod.fastly.net");
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
        index.findRule(RandomStringUtils.random(2));
    }

}
