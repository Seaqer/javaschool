package lesson6.Reflection;

public interface Factorable {

    <T> T getBean(Class<T> cls);

    void close();
}
