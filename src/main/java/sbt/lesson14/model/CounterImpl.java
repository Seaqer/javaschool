package sbt.lesson14.model;


/**
 * Created by Артём on 05.10.2016.
 */
public class CounterImpl implements Counter {

    public int getCountWords(int length, String string) {
        if (string == null) {
            throw new NullPointerException();
        }

        int count = 0;
        String[] words = string.split("[^A-Za-z]+", 0);

        for (String word : words) {
            if (words[0].equals("") && word.length() == length) {
                count++;
            }
        }
        return count;
    }


    public int getCountAllWords(String string) {
        if (string == null) {
            throw new NullPointerException();
        }

        String[] words = string.split("[^A-Za-z]+", 0);
        return (words.length > 0 && words[0].equals("")) ? words.length - 1 : words.length;

    }
}
