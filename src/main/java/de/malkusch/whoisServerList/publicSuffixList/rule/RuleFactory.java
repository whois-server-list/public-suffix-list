package de.malkusch.whoisServerList.publicSuffixList.rule;


public class RuleFactory {

    public Rule build(final String pattern) {
        boolean exceptionRule = pattern.charAt(0) == Rule.EXCEPTION_TOKEN;

        Rule rule = new Rule(exceptionRule ? pattern.substring(1) : pattern);
        rule.setExceptionRule(pattern.charAt(0) == Rule.EXCEPTION_TOKEN);

        return rule;
    }

}
