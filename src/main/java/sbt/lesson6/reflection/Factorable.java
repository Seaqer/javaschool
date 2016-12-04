package sbt.lesson6.reflection;

import java.lang.reflect.InvocationTargetException;

public interface Factorable {

    <T> T getBean(Class<T> cls);

    void close() throws InvocationTargetException, IllegalAccessException;
}
