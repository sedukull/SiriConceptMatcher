package ConceptStore;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Trie tree based concept repository implementation.
 * {@inheritDoc}
 */
public class TrieBasedConceptRepository implements  ConceptRepository {

    private ConceptStoreNode root;

    public TrieBasedConceptRepository(final ConceptStoreNode rootConceptStoreNode) {
        this.root = rootConceptStoreNode;
    }

    /**
     *Get all words starting with the given prefix.
     *Find the node corresponding to the prefix, then gather up all the child words.
     */
    @Override
    public List<String> findPrefixMatches(final String prefix) {
        List<String> words = new ArrayList<>();
        ConceptStoreNode node = findNode(prefix);
        if (node != null) {
            if (node.isEnd()) {
                words.add(prefix);
            }
            getWordsRecurse(node, prefix, words);
        }
        return words;
    }

    /**
     *Get all words including and below this node, prefixed with given string,
     *and add them to the given list.
     */
    private void getWordsRecurse(final ConceptStoreNode node, final String prefix, final List<String> words) {
        Map<Character, ConceptStoreNode> children = node.getChildren();
        for (ConceptStoreNode n : children.values()) {
            String nodestring = prefix + n.getKey();
            if (n.isEnd())
                words.add(nodestring);
            if (n.getChildren().size()>0)
                getWordsRecurse(n, nodestring, words);
        }
    }

    /**
     * Finds the node with matching string
     * @param word input string to find the given node with a matched prefix.
     * @return ConceptStoreNode.
     */
    public ConceptStoreNode findNode(String word) {
        int length = word.length();
        ConceptStoreNode pCrawl = root;
        for (int level = 0; level < length; level++) {
            char ch = word.charAt(level);
            pCrawl = pCrawl.get(ch);
            if (pCrawl == null) break;
        }
        return pCrawl;
    }

    /**
     * Inserts the given word in trie.
     * @param key
     */
    public void insert(String key)
    {
        int length = key.length();
        ConceptStoreNode pCrawl = root;
        for (int level = 0; level < length; level++) {
            char ch = key.charAt(level);
            if (pCrawl.get(ch) == null)
                pCrawl.put(ch, new ConceptStoreNode(ch));
            pCrawl = pCrawl.get(ch);
        }
        // mark last node as leaf
        pCrawl.isEndOfWord = true;
    }
}
