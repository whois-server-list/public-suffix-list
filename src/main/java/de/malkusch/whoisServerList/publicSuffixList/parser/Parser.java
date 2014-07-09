package de.malkusch.whoisServerList.publicSuffixList.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.rule.RuleFactory;

public class Parser {
	
	static private Pattern rule = Pattern.compile("^(\\S+)");
	
	static private Pattern commentLine = Pattern.compile("^//.*$");
	
	static private Pattern whiteSpaceLine = Pattern.compile("^\\s*$");
	
	private RuleFactory ruleFactory = new RuleFactory();
	
	/**
	 * Parses all rules from a stream.
	 */
	public List<Rule> parse(InputStream stream) throws IOException {
		List<Rule> rules = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		String line;
		while((line = reader.readLine()) != null) {
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
	 */
	public Rule parseLine(String line) {
		if (line == null) {
			return null;
			
		}
		
		line = line.trim();
		
		if (commentLine.matcher(line).matches()) {
			return null;
			
		}
		if (whiteSpaceLine.matcher(line).matches()) {
			return null;
			
		}
		
		Matcher matcher = rule.matcher(line);
		if (! matcher.find()) {
			return null;
			
		}
		return ruleFactory.build(matcher.group(1));
	}
	
}
