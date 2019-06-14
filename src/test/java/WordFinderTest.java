import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WordFinderTest {

    @Test
    public void shouldFindWordsInFileThatEqualInput() throws IOException {
        // given
        WordFinder wordFinder = new WordFinder();
        wordFinder.setPath("/Users/jason.motee/Exercises/coundown-helper-app/src/test/resources/words_test.txt");

        // when
        List<String> actual = wordFinder.search("a");
        List<String> expected = Collections.singletonList("a");

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldFindWordsInFileThatEqualInput_whenLettersAreInAnyOrder() throws IOException {
        // given
        WordFinder wordFinder = new WordFinder();
        wordFinder.setPath("/Users/jason.motee/Exercises/coundown-helper-app/src/test/resources/words_test.txt");

        // when
        List<String> actual = wordFinder.search("ba");
        List<String> expected = Collections.singletonList("ab");

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldFindAllWordsInDictionaryThatContainInput() throws IOException {
        // given
        WordFinder wordFinder = new WordFinder();
        // when
        List<String> actual = wordFinder.search("etdaupeet");
        List<String> expected = new ArrayList<>(Arrays.asList("teated", "patted", "update", "pedate", "pattee", "petted", "depute", "putted", "puttee"));

        // then
        Assertions.assertThat(actual).containsAnyElementsOf(expected);
        Assertions.assertThat(actual).doesNotContain("tapped");
    }

    @Test
    public void shouldReturnAllWordsInDictionaryThatAreTheBiggest() throws IOException {
        // given
        WordFinder wordFinder = new WordFinder();

        // when
        List<String> actual = wordFinder.search("aesvrnshi");
        List<String> expected = new ArrayList<>(Arrays.asList("vanishers", "varnishes"));

        // then
        Assertions.assertThat(actual).containsExactlyElementsOf(expected);
    }
}
