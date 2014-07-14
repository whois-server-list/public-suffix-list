package de.malkusch.whoisServerList.publicSuffixList.index.tree;

import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;
import de.malkusch.whoisServerList.publicSuffixList.util.DomainUtil;

/**
 * Tree Node.
 *
 * Operations on the node are case insensitive.
 *
 * @param <T> {@code Node} implementation
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
abstract class Node<T extends Node<T>> {

    /**
     * Domain label. This is the search index in its canonical form.
     *
     * @see TreeIndex#getCanonicalLabel(String)
     */
    private final String label;

    /**
     * The children mapped by their canonical labels.
     *
     * Accessing {@code Map#get(Object)} is thread-safe on this map.
     */
    private final Map<String, T> children;

    /**
     * Sets the domain label.
     *
     * @param label  the domain label, may be null for the root
     */
    Node(final String label) {
        this(label, new HashMap<String, T>());
    }

    /**
     * Sets the domain label and existing children.
     *
     * @param label  the domain label, may be null for the root
     * @param children  the children
     */
    Node(final String label, final Map<String, T> children) {
        this.label = TreeIndex.getCanonicalLabel(label);
        this.children = children;
    }

    /**
     * Returns a child.
     *
     * @param childLabel  case insensitive domain label, null returns null
     * @return the child, or null if the child doesn't exist
     */
    T getChild(final String childLabel) {
        return children.get(TreeIndex.getCanonicalLabel(childLabel));
    }

    /**
     * Returns the children.
     *
     * @return the children, not null
     */
    Collection<T> getChildren() {
        return children.values();
    }

    /**
     * Returns the canonical label.
     *
     * @return the canonical label, or null for the root
     */
    String getLabel() {
        return label;
    }

    /**
     * Adds a child.
     *
     * This is done only during building the tree in
     * {@code TreeIndexFactory#build(java.util.List)}.
     *
     * @param node  the child, not null
     */
    void addChild(final T node) {
        addChild(node, children);
    }

    /**
     * Adds a child to a children map of a parent node.
     *
     * @param child  the new child, not null
     * @param children  the children map of the parent, not null
     * @param <T> {@code Node} implementation
     */
    static <T extends Node<T>> void addChild(
            final T child, final Map<String, T> children) {

        children.put(child.getLabel(), child);
    }

    /**
     * Returns the rule.
     *
     * @return the rule, may be null
     */
    abstract Rule getRule();

    /**
     * Returns the wildcard child.
     *
     * @see Rule#WILDCARD
     * @return the wildcard, may be null.
     */
    T getWildcard() {
        return children.get(Rule.WILDCARD);
    }

    /**
     * Converts a domain name into its labels.
     *
     * @param domain  the domain name, not null
     * @return the domain labels
     */
    Deque<String> convertDomain(final String domain) {
        String[] labels = DomainUtil.splitLabels(domain);
        return new LinkedList<String>(Arrays.asList(labels));
    }

    @Override
    public String toString() {
        return label;
    }

}
