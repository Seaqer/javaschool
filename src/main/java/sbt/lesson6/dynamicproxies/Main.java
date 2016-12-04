package sbt.lesson6.dynamicproxies;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        Counter counter = new CounterImpl();
        Counter counterCache = (Counter) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{Counter.class},
                new CacheHandler(counter)
        );

        counterCache.getCountAllWords("When plans for its replacement by a modern steelworks");
        counterCache.getCountAllWords("its replacement by");
        counterCache.getCountAllWords("When plans for its replacement by a modern steelworks ");
    }
}
