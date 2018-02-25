import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import java.util.Optional;
import java.util.List;

import ConceptStore.ConceptStoreNode;
import ConceptStore.TrieBasedConceptRepository;

public class ConceptRepositoryTest {

    private ConceptStoreNode root = new ConceptStoreNode();
    private TrieBasedConceptRepository trieBasedConceptRepository;

    @Before
    public void setup() {
        trieBasedConceptRepository = new TrieBasedConceptRepository(root);
        String keys[] = {"the", "a", "there", "answer", "any",
            "by", "bye", "their"};

        for (int i = 0; i < keys.length ; i++)
            trieBasedConceptRepository.insert(keys[i]);
    }

    @Test
    public void ConceptRepositoryTest1() {
        List<String> matches = trieBasedConceptRepository.findPrefixMatches("the");
        Assert.assertEquals(matches.size(), 3);
    }

    @Test
    public void ConceptRepositoryTest2() {
        List<String> matches = trieBasedConceptRepository.findPrefixMatches("answer");
        Assert.assertEquals(matches.size(), 1);
        Assert.assertEquals(matches.get(0), "answer");
    }

    @Test
    public void ConceptRepositoryTest3() {
        List<String> matches = trieBasedConceptRepository.findPrefixMatches("zhy");
        Assert.assertEquals(matches.size(), 0);
    }
}
