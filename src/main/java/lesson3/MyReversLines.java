package lesson3;

import java.util.*;

/**
 * Created by Артём on 20.09.2016
 */
class MyReversLines {


    /**
     * Task №5
     */
    static void getIterator(String text) {
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));
        Iterator<String> iterator = new MyListIterator(lines);

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
