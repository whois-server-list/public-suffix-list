package de.malkusch.whoisServerList.publicSuffixList;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;

public class PublicSuffixUDF {
    PublicSuffixListFactory factory = new PublicSuffixListFactory();
    PublicSuffixList suffixList = factory.build();

        public String eTLDp1(String domain) throws IOException {
            return suffixList.getRegistrableDomain(domain);
        }
}
