package de.malkusch.whoisServerList.publicSuffixList.index;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.test.util.TestUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IndexFactoryTest {

    public static Collection<IndexFactory> CASES() {
        return TestUtil.getTestIndexFactories();
    }

    @ParameterizedTest
    @MethodSource("CASES")
    public void testBuild(IndexFactory factory) throws IOException {
        List<Rule> rules = TestUtil.getDefaultParsedRules();
        Set<Rule> expected = new HashSet<>(rules);

        Index index = factory.build(rules);
        Set<Rule> actual = new HashSet<>(index.getRules());

        assertEquals(expected, actual);
    }
}
