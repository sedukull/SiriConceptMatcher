import java.util.List;

/**
 *Concept search interface for look up of the trie tree for a given word.
 * Uses the input string provided by user.
 * Handles the input request from concept list lookup router and
 * returns all matches found, else an empty list.
 */
public interface ConceptMatcherAlgorithm {

    List<String> lookUp(final String inputString) throws IllegalArgumentException;
}
