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

/**
 * Tree Node.
 *
 * Operations on the node are case insensitive.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
class Node {

    /**
     * Rule, may be null.
     */
    private Rule rule;

    /**
     * Domain label. This is the search index.
     */
    private String label;

    /**
     * Case insensitive children nodes.
     */
    private Map<String, Node> children
        = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Sets the domain label.
     *
     * @param label  the domain label, may be null for the root
     */
    protected Node(final String label) {
        this.label = label;
    }

    /**
     * Returns the rule.
     *
     * @return the rule, may be null
     */
    protected Rule getRule() {
        return rule;
    }

    /**
     * Sets the rule.
     *
     * @param rule  the rule, may be null
     */
    protected void setRule(final Rule rule) {
        this.rule = rule;
    }

    /**
     * Returns the wildcard child.
     *
     * @see Rule#WILDCARD
     * @return the wildcard, may be null.
     */
    protected Node getWildcard() {
        return children.get(Rule.WILDCARD);
    }

    /**
     * Adds a child.
     *
     * @param node  the child, not null
     */
    protected void addChild(final Node node) {
        children.put(node.label, node);
    }

    /**
     * Finds a list of nodes which match the domain.
     *
     * @param domain  the domain name, may be null
     * @return  the {@code Node}s, not null
     */
    protected Collection<Node> findNodes(final String domain) {
        return findNodes(convert(domain));
    }

    /**
     * Finds a list of nodes which match the domain labels.
     *
     * @param labels  the domain labels, not null
     * @return  the {@code Node}s, not null
     */
    private Collection<Node> findNodes(final Deque<String> labels) {
        Collection<Node> nodes = new LinkedList<>();
        if (labels.isEmpty()) {
            return nodes;

        }
        String searchLabel = labels.removeLast();
        Node child = children.get(searchLabel);
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

    /**
     * Returns all descendants of this node.
     *
     * @return the descendants, not null
     */
    protected Collection<Node> getDescendants() {
        Collection<Node> descendants = new ArrayList<Node>(children.values());
        for (Node child : children.values()) {
            descendants.addAll(child.getDescendants());

        }
        return descendants;
    }

    /**
     * Converts a domain name into its labels.
     *
     * @param domain  the domain name, not null
     * @return the domain labels
     */
    private Deque<String> convert(final String domain) {
        String[] labels = DomainUtil.splitLabels(domain);
        return new LinkedList<String>(Arrays.asList(labels));
    }

    /**
     * Returns the node for a rule pattern.
     *
     * If the node and its path doesn't exist yet, it will be created.
     *
     * @param rule rule pattern, not null
     * @return the {@code Node}, not null
     */
    protected Node getOrCreateDescendant(final String rule) {
        return getOrCreateDescendant(convert(rule));
    }

    /**
     * Returns the node for rule labels.
     *
     * If the node and its path doesn't exist yet, it will be created.
     *
     * @param labels rule labels, not null
     * @return the {@code Node}, not null
     */
    private Node getOrCreateDescendant(final Deque<String> labels) {
        if (labels.isEmpty()) {
            return this;

        }
        Node child = getOrCreateChild(labels.removeLast());
        return child.getOrCreateDescendant(labels);
    }

    /**
     * Returns the child for a label.
     *
     * If the child doesn't exist yet, it will be created.
     *
     * @param childLabel  the rule label, not null
     * @return the child, not null
     */
    protected Node getOrCreateChild(final String childLabel) {
        Node child = children.get(childLabel);
        if (child == null) {
            child = new Node(childLabel);
            addChild(child);

        }
        return child;
    }

    @Override
    public String toString() {
        return label;
    }

}
