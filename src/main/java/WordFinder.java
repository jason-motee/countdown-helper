import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class WordFinder {

    private String path = "/Users/jason.motee/Exercises/coundown-helper-app/src/main/resources/words_alpha.txt";

    void setPath(String path) {
        this.path = path;
    }

    List<String> search(String target) throws IOException {
        List<String> foundWords = Files.lines(Paths.get(path))
                .map(dictionaryWord -> findWord(dictionaryWord, target))
                .filter(Objects::nonNull)
                .sorted(new StringLengthComparator())
                .collect(Collectors.toList());

        return foundWords.stream()
                .filter(words -> words.length() == foundWords.get(0).length())
                .collect(Collectors.toList());
    }

    private String findWord(String dictionaryWord, String wordJumble) {
        List<String> splitDictionaryWord = Arrays.stream(dictionaryWord.split("")).collect(Collectors.toList());
        List<String> splitWordJumble = Arrays.stream(wordJumble.split("")).collect(Collectors.toList());
        List<String> matchingLetters = new ArrayList<>();

        findMatchingLetters(splitDictionaryWord, splitWordJumble, matchingLetters);
        String joinedMatchingLetters = String.join("", matchingLetters);

        return joinedMatchingLetters.length() == dictionaryWord.length() ? joinedMatchingLetters : null;
    }

    private void findMatchingLetters(List<String> splitDictionaryWord, List<String> splitWordJumble, List<String> matchingLetters) {
        int index = 0;
        while (index < splitDictionaryWord.size()) {
            for (String letter : splitWordJumble) {
                if (splitDictionaryWord.get(index).equals(letter)) {
                    String matchingLetter = splitWordJumble.remove(splitWordJumble.indexOf(letter));
                    matchingLetters.add(matchingLetter);
                    break;
                }
            }
            index++;
        }
    }
}

class StringLengthComparator implements Comparator<String> {

    @Override
    public int compare(String firstString, String secondString) {
        return secondString.length() - firstString.length();
    }
}