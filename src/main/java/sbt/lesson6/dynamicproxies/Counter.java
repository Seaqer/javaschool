package sbt.lesson6.dynamicproxies;


/**
 * Created by Артём on 05.10.2016.
 */
public interface Counter {

    @Cache
    int getCountWords(int length, String string);

    @Cache
    int getCountAllWords(String string);
}
