package ConceptStore;

import java.util.Map;
import java.util.HashMap;


/**
 *Represents the concept store node.
 *Nodes in trie tree, represents the list of concepts in a trie tree.
 */
public class ConceptStoreNode {

    private char key;

    // Alphabet size (# of symbols)
    static final int ALPHABET_SIZE = 26;

    private Map<Character, ConceptStoreNode> children = new HashMap<>();

    //isEndOfWord is true if the node represents end of a word.
    boolean isEndOfWord;

    public ConceptStoreNode() {
        isEndOfWord = false;
        this.key = '/';
    }

    public ConceptStoreNode(final char ch) {
        key = ch;
        isEndOfWord = false;
    }

    public Map<Character, ConceptStoreNode> getChildren() {
        return this.children;
    }

    public boolean isEnd() {
        return isEndOfWord;
    }

    public char getKey() {
        return this.key;
    }

    public ConceptStoreNode get(char ch) {
        return children.get(ch);
    }

    public void put(char ch, ConceptStoreNode node) {
        children.put(ch, node);
    }
};
