package de.malkusch.whoisServerList.publicSuffixList.test.util;

import java.io.IOException;

import com.carrotsearch.junitbenchmarks.IResultsConsumer;
import com.carrotsearch.junitbenchmarks.Result;

public class NoopConsumer implements IResultsConsumer {

    public final static NoopConsumer INSTANCE = new NoopConsumer();

    private NoopConsumer() {
    }

    @Override
    public void accept(final Result result) throws IOException {
        // Noop
    }

}
