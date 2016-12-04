package sbt.lesson10;

/**
 * Created by Артём on 20.10.2016.
 */
public interface Stream {

    /**
     * Метод для создания объекта Streams из коллекции
     * @param source Коллекция элементов
     * @param <T> Тип объектов коллеции
     * @return новый объект Streams
     */
    static <T> StreamImpl<T> of(Iterable<? extends T> source) {
        return new StreamImpl<>(source, p -> p);
    }
}
