import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class WordFinder {

    private String path;

    WordFinder() {
        this("words_alpha.txt");
    }

    public WordFinder(String path) {
        this.path = path;
    }

    List<String> search(String target) throws IOException, URISyntaxException {
        List<String> foundWords = Files.lines(Paths.get(ClassLoader.getSystemResource(path).toURI()))
                .map(dictionaryWord -> findWord(dictionaryWord, target))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(String::length).reversed())
                .collect(Collectors.toList());

        //do we really need to process the stream twice?
        return foundWords.stream()
                .filter(words -> words.length() == foundWords.get(0).length())
                .collect(Collectors.toList());
    }

    private String findWord(String dictionaryWord, String wordJumble) {
        //gratuitous streamage?
        List<String> splitDictionaryWord = Arrays.stream(dictionaryWord.split("")).collect(Collectors.toList());
        List<String> splitWordJumble = Arrays.stream(wordJumble.split("")).collect(Collectors.toList());
        List<String> matchingLetters = new ArrayList<>();

        findMatchingLetters(splitDictionaryWord, splitWordJumble, matchingLetters);
        String joinedMatchingLetters = String.join("", matchingLetters);

        return joinedMatchingLetters.length() == dictionaryWord.length() ? joinedMatchingLetters : null;
    }

    //passing matching letters as a param?
    private void findMatchingLetters(List<String> splitDictionaryWord, List<String> splitWordJumble, List<String> matchingLetters) {
        int index = 0;
        //nested loops O(n^2)
        //why the switch from streams to conventional loops?
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

//bin this - see Comparator.comparing helper methods or you could supply a lambda
class StringLengthComparator implements Comparator<String> {

    @Override
    public int compare(String firstString, String secondString) {
        return secondString.length() - firstString.length();
    }
}