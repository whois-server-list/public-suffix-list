package de.malkusch.whoisServerList.publicSuffixList.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleFactory;

/**
 * The parser for the Public Suffix List file.
 *
 * The parser takes the Public Suffix List file as input and returns a
 * {@link Rule} list.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
public final class Parser {

    /**
     * The rule line pattern.
     */
    private static Pattern ruleLine = Pattern.compile("^(\\S+)");

    /**
     * The comment line pattern.
     */
    private static Pattern commentLine = Pattern.compile("^//.*$");

    /**
     * The white space line pattern.
     */
    private static Pattern whiteSpaceLine = Pattern.compile("^\\s*$");

    /**
     * The rule factory.
     */
    private RuleFactory ruleFactory = new RuleFactory();

    /**
     * Parses all rules from a stream.
     *
     * @param stream  the stream with lines of rules, not null
     * @param charset  the character encoding of that stream, not null
     * @return the {@code Rule} list, not null
     *
     * @throws IOException If reading from the stream fails
     */
    public List<Rule> parse(final InputStream stream, final Charset charset)
            throws IOException {

        List<Rule> rules = new ArrayList<>();
        BufferedReader reader
            = new BufferedReader(new InputStreamReader(stream, charset));

        String line;
        while ((line = reader.readLine()) != null) {
            Rule rule = parseLine(line);
            if (rule == null) {
                continue;

            }
            rules.add(parseLine(line));

        }
        return rules;
    }

    /**
     * Parses a line for a rule.
     *
     * @param line  the line with one rule, may be null
     * @return the parsed {@code Rule}, or null if no rule was found
     *
     */
    public Rule parseLine(final String line) {
        if (line == null) {
            return null;

        }

        String trimmedline = line.trim();

        if (commentLine.matcher(trimmedline).matches()) {
            return null;

        }
        if (whiteSpaceLine.matcher(trimmedline).matches()) {
            return null;

        }

        Matcher matcher = ruleLine.matcher(trimmedline);
        if (!matcher.find()) {
            return null;

        }
        return ruleFactory.build(matcher.group(1));
    }

}
