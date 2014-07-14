package de.malkusch.whoisServerList.publicSuffixList.test.index;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.IndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.util.TestUtil;

@RunWith(Parameterized.class)
public class IndexFactoryTest {

    @Parameter
    public IndexFactory factory;

    @Parameters
    public static Collection<IndexFactory[]> getIndexes() {
        return TestUtil.getTestIndexFactories();
    }

    @Test
    public void testBuild() throws IOException {
        List<Rule> rules = TestUtil.getDefaultParsedRules();
        Set<Rule> expected = new HashSet<>(rules);

        Index index = factory.build(rules);
        Set<Rule> actual = new HashSet<>(index.getRules());

        assertEquals(expected, actual);
    }

}
