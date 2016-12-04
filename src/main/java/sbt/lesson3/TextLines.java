package sbt.lesson3;

import java.util.*;

/**
 * Created by Артём on 18.09.2016
 */
class TextLines {


    /**
     * Task №6
     * Выводит на экран строки, номера которых задаются пользователем в произвольном порядке.
     */
    static void showLines(String[] lineNumbers, String text) {
        List<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));

        for (String numberLine : lineNumbers) {
            int number = Integer.parseInt(numberLine);
            if (number < 1 || number > lines.size()) throw new NoSuchElementException(numberLine);
            System.out.println(lines.get(number - 1));
        }
    }
}
