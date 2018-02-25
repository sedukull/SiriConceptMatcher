import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import java.util.List;

import ConceptStore.ConceptStoreNode;
import ConceptStore.TrieBasedConceptRepository;
import org.junit.rules.ExpectedException;

public class ConceptMatcherTest {

    private ConceptStoreNode root = new ConceptStoreNode();
    private TrieBasedConceptRepository trieBasedConceptRepository;
    private ConceptMatcherAlgorithm conceptMatcherAlgorithm;

    @Before
    public void setup() {
        trieBasedConceptRepository = new TrieBasedConceptRepository(root);
        String keys[] = {"the", "there", "answer", "any",
            "by", "bye", "their", "throwall", "throw", "indian", "thai", "sushi", "caribbean", "italian",
        "westindian", "eastasian", "chinese", "portuguese", "spanish", "french", "easteuropean"};

        for (int i = 0; i < keys.length ; i++)
            trieBasedConceptRepository.insert(keys[i]);
        conceptMatcherAlgorithm = new TrieBasedConceptMatcherAlgorithmImpl(trieBasedConceptRepository);
    }

    @Test
    public void ConceptMatcherTestForPrefixMatch() {
        //Input String: "The wall is good"
        //Expected concept string match : "The"
        String inputString = "The wall is good";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals(1, matchedWordList.size());
        Assert.assertEquals("The", matchedWordList.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConceptMatcherTestException() {
        //Input String: ""
        //Expected: IllegalArgumentException
        String inputString = " ";
        conceptMatcherAlgorithm.lookUp(inputString);
    }

    @Test
    public void ConceptMatcherTestWithPunctuation() {
        //Input String: "The$ wall is good"
        //Expected: "The" // i.e., puncation($) is ignored
        String inputString = "The$ wall is good";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals("The", matchedWordList.get(0));
    }

    @Test
    public void ConceptMatcherTestWithMultipleMatches() {
        //Input String: "Throw all bags"
        //Expected: "Throw all"
        String inputString = "Throw all bags";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals( 2, matchedWordList.size());
        Assert.assertEquals(matchedWordList.get(0), "Throw");
        Assert.assertEquals(matchedWordList.get(1), "Throw all");
    }

    @Test
    public void ConceptMatcherTest1() {
        //Input String: “I would like some thai food”
        //Expected: "Thai"
        String inputString = "I would like some thai food";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals( 1, matchedWordList.size());
        Assert.assertEquals(matchedWordList.get(0), "thai");
    }

    @Test
    public void ConceptMatcherTest2() {
        //Input String: “Where can I find good sushi”
        //Expected: "Sushi"
        String inputString = "Where can I find good sushi";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals( 1, matchedWordList.size());
        Assert.assertEquals(matchedWordList.get(0), "sushi");
    }

    @Test
    public void ConceptMatcherTest3() {
        //Input String: “Find me a place that does tapas”
        //Expected: None
        String inputString = "Find me a place that does tapas";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals( 0, matchedWordList.size());
    }

    @Test
    public void ConceptMatcherTest4() {
        //Input String: “Which restaurants do East Asian food”
        //Expected: "East Asian"
        String inputString = "Which restaurants do East Asian food";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals( 1, matchedWordList.size());
        Assert.assertEquals(matchedWordList.get(0), "East Asian");
    }

    @Test
    public void ConceptMatcherTest5() {
        //Input String: “Which restaurants do West Indian food”
        //Expected: "Wast Indian"
        String inputString = "Which restaurants do West Indian food";
        List<String>  matchedWordList = conceptMatcherAlgorithm.lookUp(inputString);
        Assert.assertEquals( 2, matchedWordList.size());
        Assert.assertEquals(matchedWordList.get(0), "West Indian");
        Assert.assertEquals(matchedWordList.get(1), "Indian");
    }
}
