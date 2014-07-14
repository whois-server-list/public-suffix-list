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

public class Parser {

    private static Pattern rule = Pattern.compile("^(\\S+)");

    private static Pattern commentLine = Pattern.compile("^//.*$");

    private static Pattern whiteSpaceLine = Pattern.compile("^\\s*$");

    private RuleFactory ruleFactory = new RuleFactory();

    /**
     * Parses all rules from a stream.
     *
     * @param stream Stream with lines of rules
     * @param charset Encoding of that stream
     * @throws IOException If reading from the stream fails
     */
    public List<Rule> parse(final InputStream stream, final Charset charset) throws IOException {
        List<Rule> rules = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));

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
     * Parse a line for a rule.
     *
     * Returns null if no rule was found.
     *
     * @param line Line with one rule
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

        Matcher matcher = rule.matcher(trimmedline);
        if (!matcher.find()) {
            return null;

        }
        return ruleFactory.build(matcher.group(1));
    }

}
