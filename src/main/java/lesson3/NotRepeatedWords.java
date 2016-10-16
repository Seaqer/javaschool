package lesson3;

import java.util.*;

/**
 * Created by Артём on 20.09.2016
 */
class NotRepeatedWords {


    /**
     * Task №1
     * Выводит количество различных слов в файле.
     */
    static void getAllWords(String text) {
        String[] words = text.split("[^\\w-]+");
        Set<String> notRepeatedWords = new HashSet<>(Arrays.asList(words));

        System.out.println(notRepeatedWords.size());
    }
}
