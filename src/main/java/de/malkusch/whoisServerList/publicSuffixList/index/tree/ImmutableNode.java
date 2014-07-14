package de.malkusch.whoisServerList.publicSuffixList.index.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

import net.jcip.annotations.Immutable;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * Immutable tree Node.
 *
 * The search tree uses immutable nodes during search operation.
 *
 * @author markus@malkusch.de
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
final class ImmutableNode extends Node<ImmutableNode> {

    /**
     * Rule, may be null.
     */
    private final Rule rule;

    /**
     * Initializes the node.
     *
     * @param label  the domain label, may be null for the root
     * @param children  the children, not null
     * @param rule the rule, may be null
     */
    ImmutableNode(final String label,
            final Map<String, ImmutableNode> children, final Rule rule) {
        super(label, Collections.unmodifiableMap(children));

        this.rule = rule;
    }

    @Override
    Rule getRule() {
        return rule;
    }

    /**
     * Finds a list of nodes which match the domain.
     *
     * @param domain  the domain name, may be null
     * @return  the {@code Node}s, not null
     */
    Collection<ImmutableNode> findNodes(final String domain) {
        return findNodes(convertDomain(domain));
    }

    /**
     * Finds a list of nodes which match the domain labels.
     *
     * @param labels  the domain labels, not null
     * @return  the {@code Node}s, not null
     */
    Collection<ImmutableNode> findNodes(final Deque<String> labels) {
        Collection<ImmutableNode> nodes = new LinkedList<>();
        if (labels.isEmpty()) {
            return nodes;

        }
        String searchLabel = labels.removeLast();
        ImmutableNode child = getChild(searchLabel);
        if (child != null) {
            nodes.add(child);

        }
        ImmutableNode wildcard = getWildcard();
        if (wildcard != null) {
            nodes.add(wildcard);

        }
        for (ImmutableNode node : new ArrayList<>(nodes)) {
            nodes.addAll(node.findNodes(labels));

        }
        return nodes;
    }

    /**
     * Returns all descendants of this node.
     *
     * @return the descendants, not null
     */
    Collection<ImmutableNode> getDescendants() {
        Collection<ImmutableNode> descendants = new ArrayList<>(getChildren());

        for (ImmutableNode child : getChildren()) {
            descendants.addAll(child.getDescendants());

        }
        return descendants;
    }

}
