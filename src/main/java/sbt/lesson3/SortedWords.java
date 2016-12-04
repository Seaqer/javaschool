package sbt.lesson3;

import java.util.*;

/**
 * Created by Артём on 20.09.2016
 */
class SortedWords {


    /**
     * Task №2
     * Выводит на экран список различных слов файла, отсортированный по возрастанию их длины.
     */
    static void getSortedWords(String text) {
        String[] words = text.split("[^\\w-]+");
        Set<String> sortWords = new TreeSet<>(Comparator.comparing(String::length).thenComparing(String::compareTo));

        sortWords.addAll(Arrays.asList(words));
        for (String word : sortWords) System.out.println(word);
    }
}
