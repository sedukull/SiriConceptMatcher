package ConceptStore;

import java.util.List;

/**
 * Repository to retrieve or lookup the concept list for a given word.
 * Concept Repository abstracts the underlying repository implementation.
 * Underlying implementation of concept list can be trie, bloom filter or any other data structure.
 */
public interface ConceptRepository {

    /**
     * Searches a word in concept list trie tree and returns all the strings matched in concept list with given prefix.
     * @param word
     * @return List of strings matched with given prefix.
     */
    List<String> findPrefixMatches(String word);
}
