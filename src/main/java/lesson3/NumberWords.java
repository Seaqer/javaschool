package lesson3;

import java.util.*;

/**
 * Created by Артём on 20.09.2016
 */
class NumberWords {


    /**
     * Task №3
     * Выводит на экран сколько раз каждое слово встречается в файле.
     */
    static void getNumberWords(String text) {
        String[] words = text.split("\\W+");
        Map<String, Integer> numberWords = new HashMap<>();

        for (String word : words) {
            if (numberWords.containsKey(word)) {
                int count = numberWords.get(word);
                numberWords.put(word, ++count);
            } else {
                numberWords.put(word, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : numberWords.entrySet()) {
            System.out.printf("%s - %d раз\n", entry.getKey(), entry.getValue());
        }
    }
}
