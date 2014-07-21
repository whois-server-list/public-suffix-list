package de.malkusch.whoisServerList.publicSuffixList.index.tree;

import java.util.Deque;

import javax.annotation.concurrent.NotThreadSafe;

import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * Mutable tree Node.
 *
 * The tree is mutable during construction
 * in {@link TreeIndexFactory#build(java.util.List)}.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@NotThreadSafe
final class MutableNode extends Node<MutableNode> {

    /**
     * Rule, may be null.
     *
     * The rule is set only once during building the tree.
     */
    private Rule rule;

    /**
     * Sets the domain label.
     *
     * @param label  the domain label, may be null for the root
     */
    MutableNode(final String label) {
        super(label);
    }

    /**
     * Sets the rule.
     *
     * @param rule  the rule, may be null
     */
    void setRule(final Rule rule) {
        this.rule = rule;
    }

    /**
     * Returns the rule.
     *
     * @return the rule, may be null
     */
    Rule getRule() {
        return rule;
    }

    /**
     * Returns the node for a rule pattern.
     *
     * If the node and its path doesn't exist yet, it will be created.
     *
     * @param rulePattern  the rule pattern, not null
     * @return the node, not null
     */
    MutableNode getOrCreateDescendant(final String rulePattern) {
        return getOrCreateDescendant(convertDomain(rulePattern));
    }

    /**
     * Returns the node for rule labels.
     *
     * If the node and its path doesn't exist yet, it will be created.
     *
     * @param labels  the rule labels, not null
     * @return the node, not null
     */
    private MutableNode getOrCreateDescendant(final Deque<String> labels) {
        if (labels.isEmpty()) {
            return this;

        }
        MutableNode child = getOrCreateChild(labels.removeLast());
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
    MutableNode getOrCreateChild(final String childLabel) {
        MutableNode child = getChild(childLabel);
        if (child == null) {
            child = new MutableNode(childLabel);
            addChild(child);

        }
        return child;
    }

}
