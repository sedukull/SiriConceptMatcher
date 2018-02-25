import ConceptStore.ConceptRepository;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 * Implementation for ConceptMatcherAlgorithm called by request router for the given input string.
 *
 */
public class TrieBasedConceptMatcherAlgorithmImpl implements ConceptMatcherAlgorithm {

    private ConceptRepository conceptRepository;

    public TrieBasedConceptMatcherAlgorithmImpl(final ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public List<String> lookUp(final String inputString) throws IllegalArgumentException {
        // Validate the input string
        if ((inputString != null) && (inputString.isEmpty())) {
            throw new IllegalArgumentException("Invalid input string, please check.");
        }

        // Split the string based upon space(s).
        String[] inputWordList = inputString.split("\\s+");

        // Sanitize input string list, remove punctuations inside words, eliminate invalid words
        // and collect all valid words.
        List<String> validWordList = Arrays.stream(inputWordList)
            .map(word -> word.replaceAll("\\p{Punct}", ""))
            .filter(word -> isValidWord(word))
            .collect(Collectors.toList());

        if (validWordList.isEmpty()) {
            throw new IllegalArgumentException("Invalid words in input string, please check. Words with only [a-zA-Z] cases are allowed.");
        }

        List<String> matchedWordList = new ArrayList<>();
        for (String currentWord : validWordList) {
            List<String> prefixMatchesList = conceptRepository.findPrefixMatches(currentWord.toLowerCase());
            if (prefixMatchesList != null && !prefixMatchesList.isEmpty()) {
                if ((prefixMatchesList.indexOf(currentWord.toLowerCase())) != -1) {
                    matchedWordList.add(currentWord);
                }
                List<String> tempList = findAllMatches(currentWord, validWordList, prefixMatchesList);
                matchedWordList.addAll(tempList);
            }
        }
        return matchedWordList;
    }

    public List<String> findAllMatches(final String currentWord, final List<String> validWordList, final List<String> prefixMatches) {
        List<String> matchedWordList = new ArrayList<>();
        int index = validWordList.indexOf(currentWord);
        if (index < validWordList.size() && index > -1) {
            List<String> tempList = validWordList.subList(validWordList.indexOf(currentWord)+1, validWordList.size());
            String tempStr = currentWord;
            String toAddd = currentWord;
            for (String word : tempList) {
                tempStr = tempStr + word;
                toAddd = toAddd + " " + word;
                int index1 = prefixMatches.indexOf(tempStr.toLowerCase());
                if (index1 != -1)
                    matchedWordList.add(toAddd);
            }
        }
        return matchedWordList;
    }

    /**
     * Validates whether the provided input string is valid word([a-zA-Z]) or not.
     * @param word string to validate.
     * @return true if valid else false.
     */
    public boolean isValidWord(final String word) {
        return word.chars().allMatch(Character::isLetter);
    }
}
