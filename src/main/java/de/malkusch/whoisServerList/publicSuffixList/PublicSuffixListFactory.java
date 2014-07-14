package de.malkusch.whoisServerList.publicSuffixList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import net.jcip.annotations.Immutable;
import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.IndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * The factory for {@code PublicSuffixList}.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 * @see PublicSuffixList
 */
@Immutable
public final class PublicSuffixListFactory {

    /**
     * URL of the Public Suffix List.
     *
     * @see PublicSuffixList#getURL()
     */
    public static final String PROPERTY_URL = "psl.url";

    /**
     * Character encoding of the list.
     *
     * @see PublicSuffixList#getCharset()
     */
    public static final String PROPERTY_CHARSET = "psl.charset";

    /**
     * The factory will load the Public Suffix List from this file.
     */
    public static final String PROPERTY_LIST_FILE = "psl.file";

    /**
     * Index implementation.
     *
     * @see #PROPERTY_INDEX_FACTORY
     * @deprecated use {@link #PROPERTY_INDEX_FACTORY}.
     */
    @Deprecated
    public static final String PROPERTY_INDEX = "psl.index";

    /**
     * Index factory.
     *
     * @see IndexFactory
     * @since 2.0.0
     */
    public static final String PROPERTY_INDEX_FACTORY = "psl.indexFactory";


    /**
     * Location of the default properties.
     *
     * @see #build()
     */
    public static final String PROPERTY_FILE = "/psl.properties";

    /**
     * The default properties.
     *
     * The default properties are included in the jar file at /psl.properties.
     * It uses a bundled list file and a {@code TreeIndexFactory}.
     *
     * @return default {@code Properties}
     */
    public Properties getDefaults() {
        try (InputStream stream
                = getClass().getResourceAsStream(PROPERTY_FILE)) {

            Properties properties = new Properties();
            properties.load(stream);
            return properties;

        } catch (IOException e) {
            throw new IllegalStateException(e);

        }
    }

    /**
     * Builds a PublicSuffixList with custom properties.
     *
     * It is a good idea to load the default properties and
     * overwrite keys as required.
     *
     * @param properties Properties for building the {@link PublicSuffixList}.
     * @see #getDefaults()
     * @throws ClassNotFoundException If {@link #PROPERTY_INDEX_FACTORY}
     *  is not a {@link IndexFactory} implementation
     * @throws IOException If {@link #PROPERTY_LIST_FILE} can't be read.
     * @return The {@code PublicSuffixList} build with the {@code Properties}
     */
    public PublicSuffixList build(final Properties properties)
            throws IOException, ClassNotFoundException {

        String propertyFile = properties.getProperty(PROPERTY_LIST_FILE);
        try (InputStream listStream
                = getClass().getResourceAsStream(propertyFile)) {

            URL url = new URL(properties.getProperty(PROPERTY_URL));

            Charset charset
                = Charset.forName(properties.getProperty(PROPERTY_CHARSET));

            Parser parser = new Parser();
            List<Rule> rules = parser.parse(listStream, charset);

            // add default rule
            rules.add(Rule.DEFAULT);

            String indexFactoryClassName
                = properties.getProperty(PROPERTY_INDEX_FACTORY);
            @SuppressWarnings("unchecked")
            Class<IndexFactory> indexFactoryClass
                = (Class<IndexFactory>) Class.forName(indexFactoryClassName);
            IndexFactory indexFactory = indexFactoryClass.newInstance();

            Index index = indexFactory.build(rules);

            PublicSuffixList list = new PublicSuffixList(index, url, charset);

            return list;

        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);

        }
    }

    /**
     * Builds a PublicSuffixList with the default properties.
     *
     * @see #PROPERTY_FILE
     * @see #getDefaults()
     * @return The {@code PublicSuffixList} build
     *  with the default {@code Properties}
     */
    public PublicSuffixList build() {
        try {
            return build(getDefaults());

        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalStateException(e);

        }
    }

}
