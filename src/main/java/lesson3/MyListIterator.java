package lesson3;

import java.util.*;

/**
 * Created by Артём on 20.09.2016
 */
class MyListIterator implements Iterator<String> {
    private List<String> array;
    private int cursor;
    private int expectedModCount;


    MyListIterator(List<String> array) {
        this.array = array;
        cursor = array.size();
        expectedModCount = array.size();
    }

    public String next() {
        cursor--;

        if (array.size() != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        if (cursor < 0) {
            throw new NoSuchElementException();
        }
        return array.get(cursor);
    }

    public boolean hasNext() {
        return cursor > 0;
    }
}
