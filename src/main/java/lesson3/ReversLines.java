package lesson3;

import java.util.*;

/**
 * Created by Артём on 20.09.2016
 */
class ReversLines {


    /**
     * Task №4
     * Выводит на экран все строки в обратном порядке.
     */
    static void getReversLines(String text) {
        List<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));
        ListIterator<String> iterator = lines.listIterator(lines.size());

        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }
    }
}
