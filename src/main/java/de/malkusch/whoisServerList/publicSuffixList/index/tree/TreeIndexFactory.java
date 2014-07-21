package de.malkusch.whoisServerList.publicSuffixList.index.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import de.malkusch.whoisServerList.publicSuffixList.index.Index;
import de.malkusch.whoisServerList.publicSuffixList.index.IndexFactory;
import de.malkusch.whoisServerList.publicSuffixList.rule.Rule;

/**
 * Builds a tree based implementation with O(log(n)) complexity.
 *
 * @author markus@malkusch.de
 *
 * @see TreeIndex
 * @see <a href="bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK">Donations</a>
 */
@Immutable
public final class TreeIndexFactory implements IndexFactory {

    @Override
    public Index build(final List<Rule> rules) {
        MutableNode root = new MutableNode(null);
        for (Rule rule : rules) {
            MutableNode node = root.getOrCreateDescendant(rule.getPattern());
            node.setRule(rule);

        }
        return new TreeIndex(convert(root));
    }

    /**
     * Converts a {@code MutableNode} into an {@code ImmutableNode}.
     *
     * @param node  the mutable node, not null
     * @return the converted immutable node, not null
     */
    private ImmutableNode convert(final MutableNode node) {
        Map<String, ImmutableNode> convertedChildren = new HashMap<>();
        for (MutableNode child : node.getChildren()) {
            Node.addChild(convert(child), convertedChildren);

        }
        return new ImmutableNode(
                node.getLabel(), convertedChildren, node.getRule());

    }

}
