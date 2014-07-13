package de.malkusch.whoisServerList.publicSuffixList.index.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

public class TreeIndex extends Index {
	
	private Node root;

	@Override
	public void setRules(List<Rule> rules) {
		root = new Node(null);
		for (Rule rule : rules) {
			Node node = root.getOrCreateDescendant(rule.getPattern());
			node.setRule(rule);
			
		}
	}

	@Override
	protected Collection<Rule> findRules(String domain) {
		Collection<Rule> rules = new ArrayList<>();
		for (Node node : root.findNodes(domain)) {
			Rule rule = node.getRule();
			if (rule != null) {
				rules.add(rule);
				
			}
		}
		return rules;
	}
	
	@Override
	public List<Rule> getRules() {
		List<Rule> rules = new ArrayList<>();
		for (Node node : root.getDescendants()) {
			Rule rule = node.getRule();
			if (rule != null) {
				rules.add(rule);
				
			}
		}
		return rules;
	}

}
