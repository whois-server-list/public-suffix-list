package de.malkusch.whoisServerList.publicSuffixList.index.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

class Node {

    private Rule rule;

    private String label;

    private Map<String, Node> children = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    protected Node(final String label) {
        this.label = label;
    }

    protected Rule getRule() {
        return rule;
    }

    protected void setRule(final Rule rule) {
        this.rule = rule;
    }

    protected Node getWildcard() {
        return children.get(Rule.WILDCARD);
    }

    protected void addChild(final Node node) {
        children.put(node.label, node);
    }

    protected Collection<Node> findNodes(final String domain) {
        return findNodes(convert(domain));
    }

    private Collection<Node> findNodes(final Deque<String> labels) {
        Collection<Node> nodes = new LinkedList<>();
        if (labels.isEmpty()) {
            return nodes;

        }
        String label = labels.removeLast();
        Node child = children.get(label);
        if (child != null) {
            nodes.add(child);

        }
        Node wildcard = getWildcard();
        if (wildcard != null) {
            nodes.add(wildcard);

        }
        for (Node node : new ArrayList<>(nodes)) {
            nodes.addAll(node.findNodes(labels));

        }
        return nodes;
    }

    private Deque<String> convert(final String domain) {
        String[] labels = DomainUtil.splitLabels(domain);
        return new LinkedList<String>(Arrays.asList(labels));
    }

    protected Node getOrCreateDescendant(final String rule) {
        return getOrCreateDescendant(convert(rule));
    }

    protected Collection<Node> getDescendants() {
        Collection<Node> descendants = new ArrayList<Node>(children.values());
        for (Node child : children.values()) {
            descendants.addAll(child.getDescendants());

        }
        return descendants;
    }

    private Node getOrCreateDescendant(final Deque<String> labels) {
        if (labels.isEmpty()) {
            return this;

        }
        Node child = getOrCreateChild(labels.removeLast());
        return child.getOrCreateDescendant(labels);
    }

    protected Node getOrCreateChild(final String label) {
        Node child = children.get(label);
        if (child == null) {
            child = new Node(label);
            addChild(child);

        }
        return child;
    }

    @Override
    public String toString() {
        return label;
    }

}
