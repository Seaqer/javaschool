package lesson3;

import java.util.*;

public class Main {


    private static final String TEXT = "Dynamic arrays benefit from many of the advantages of arrays, including good \n" +
            "locality of reference and data cache utilization, compactness (low memory use), and random access. They usually have only a small fixed additional overhead for storing information about the \n" +
            "size and capacity. This makes dynamic arrays an attractive tool for building cache-friendly data structures. However, in languages like Python or Java that enforce reference semantics, the \n" +
            "dynamic array generally will not store the actual data, but rather it will store references to the data that resides in other areas of memory. ";


    public static void main(String[] args) {
        Scanner inConsole = new Scanner(System.in);

        while (true) {
            System.out.println("Номер задания 1-6: ");
            switch (Integer.parseInt(inConsole.nextLine())) {
                case 1:
                    NotRepeatedWords.getAllWords(TEXT);
                    break;
                case 2:
                    SortedWords.getSortedWords(TEXT);
                    break;
                case 3:
                    NumberWords.getNumberWords(TEXT);
                    break;
                case 4:
                    ReversLines.getReversLines(TEXT);
                    break;
                case 5:
                    MyReversLines.getIterator(TEXT);
                    break;
                case 6:
                    System.out.println("Введите строки: ");
                    String[] lines = inConsole.nextLine().split("\\D+");
                    TextLines.showLines(lines,TEXT);
                    break;
                default:
                    return;

            }
        }
    }
}

