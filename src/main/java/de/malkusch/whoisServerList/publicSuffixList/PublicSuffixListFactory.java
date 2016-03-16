package de.malkusch.whoisServerList.publicSuffixList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import javax.annotation.concurrent.Immutable;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.IndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.parser.Parser;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * The factory for {@code PublicSuffixList}.
 *
 * @author markus@malkusch.de
 *
 * @see PublicSuffixList
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
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
     * @deprecated As of release 2.0.0, replaced by
     *             {@link #PROPERTY_INDEX_FACTORY}.
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
     * Gets the bundled default properties.
     *
     * The default properties are included in the jar file at /psl.properties.
     * It refers to the bundled Public Suffix List file and uses a
     * {@code TreeIndexFactory}.
     *
     * @return the default properties
     */
    public Properties getDefaults() {
        try (InputStream stream = getClass().getResourceAsStream(PROPERTY_FILE)) {

            Properties properties = new Properties();
            properties.load(stream);
            return properties;

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * Builds a PublicSuffixList with custom properties.
     *
     * It is a good idea to load the default properties and overwrite keys as
     * required.
     *
     * @param properties
     *            the properties for building the {@link PublicSuffixList}.
     * @return a public suffix list build with the properties
     *
     * @throws ClassNotFoundException
     *             If the property {@link #PROPERTY_INDEX_FACTORY} is not a
     *             {@code IndexFactory} implementation
     * @throws IOException
     *             If the property {@link #PROPERTY_LIST_FILE} can't be read as
     *             a file
     *
     * @see #getDefaults()
     */
    public PublicSuffixList build(final Properties properties) throws IOException, ClassNotFoundException {

        String propertyFile = properties.getProperty(PROPERTY_LIST_FILE);
        try (InputStream listStream = getClass().getResourceAsStream(propertyFile)) {

            return build(listStream, properties);

        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a PublicSuffixList.
     * 
     * @param list
     *            The list.
     * @return a public suffix list
     * @throws IOException
     *             If the list can't be read as a file
     */
    public PublicSuffixList build(InputStream list) throws IOException {
        try {
            return build(list, getDefaults());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * Downloads the public suffix list.
     * 
     * @return a public suffix list
     * @throws IOException
     *             If the list can't be downloaded.
     */
    public PublicSuffixList download() throws IOException {
        Properties properties = getDefaults();

        URL url;
        try {
            url = new URL(properties.getProperty(PROPERTY_URL));

        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }

        try (InputStream listStream = url.openStream()) {
            return build(listStream, properties);

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a PublicSuffixList.
     * 
     * @param list
     *            The list.
     * @return a public suffix list
     */
    private PublicSuffixList build(InputStream list, Properties properties)
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        URL url = new URL(properties.getProperty(PROPERTY_URL));

        Charset charset = Charset.forName(properties.getProperty(PROPERTY_CHARSET));

        IndexFactory indexFactory = loadIndexFactory(properties.getProperty(PROPERTY_INDEX_FACTORY));

        return build(list, url, charset, indexFactory);
    }

    /**
     * Loads the index factory.
     * 
     * @param indexFactoryClassName
     *            the class name of the index factory.
     * @return the index factory
     */
    private IndexFactory loadIndexFactory(String indexFactoryClassName)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        Class<IndexFactory> indexFactoryClass = (Class<IndexFactory>) Class.forName(indexFactoryClassName);
        return indexFactoryClass.newInstance();
    }

    /**
     * Builds a PublicSuffixList with the default properties.
     *
     * @return a public suffix list build with the default properties
     *
     * @see #PROPERTY_FILE
     * @see #getDefaults()
     */
    public PublicSuffixList build() {
        try {
            return build(getDefaults());

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * Builds a PublicSuffixList.
     * 
     * @param list
     *            The list.
     * @param url
     *            The list url.
     * @param charset
     *            The list charset.
     * @param indexFactory
     *            The index factory.
     *
     * @return a public suffix list build with the properties
     * @throws IOException
     *             The list could not be read.
     */
    private PublicSuffixList build(final InputStream list, final URL url, final Charset charset,
            final IndexFactory indexFactory) throws IOException {
        Parser parser = new Parser();
        List<Rule> rules = parser.parse(list, charset);

        // add default rule
        rules.add(Rule.DEFAULT);

        Index index = indexFactory.build(rules);

        return new PublicSuffixList(index, url, charset);
    }

}
