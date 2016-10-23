package lesson10;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamImpl<T> {
    private final Iterator<? extends T> iterator;
    private PipelineAction action;

    StreamImpl(Iterable<? extends T> source, PipelineAction action) {
        iterator = source.iterator();
        this.action = action;
    }


    /**
     * Метод преобразующий элемент одного типа в элемент другого типа
     *
     * @param transform Функця преобразования
     * @param <R>       Новый тип объектов коллекции
     * @return Преобразованный объект Streams
     */
    @SuppressWarnings("unchecked")
    public <R> StreamImpl<R> transform(Function<? super T, ? extends R> transform) {
        PipelineAction prevAction = action;
        action = (PipelineAction<?, ?>) (T e) -> {
            if (e == null) return null;
            else {
                T element = (T) prevAction.run(e);
                return (element == null) ? null : transform.apply(element);
            }
        };
        return (StreamImpl<R>) this;
    }

    /**
     * Метод фильтрующий список в соотвествии с условием предиката.
     *
     * @param predicate Условие фильтрации
     * @return Отфильтрованный объект Streams
     */
    @SuppressWarnings("unchecked")
    public StreamImpl<T> filter(Predicate<? super T> predicate) {
        PipelineAction prevAction = action;
        action = (PipelineAction<?, ?>) (T e) -> (e == null) ? null : predicate.test(e) ? prevAction.run(e) : null;
        return this;
    }

    /**
     * Метод создающий коллекцию Map из объекта Streams
     *
     * @param keyFunction   Функция выбора ключа
     * @param valueFunction Функция выбора значения
     * @param <K>           Тип ключей в коллекции Map
     * @param <V>           Тип значений в коллекции Map
     * @return Коллекция Map<Key,Value>
     */
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyFunction, Function<? super T, ? extends V> valueFunction) {
        Map<K, V> map = new HashMap<>();
        while (iterator.hasNext()) {
            T result = iterator.next();
            if (result == null) {
                map.put(null, null);
            } else {
                result = (T) action.run(result);
                if (result != null) {
                    map.put(keyFunction.apply(result), valueFunction.apply(result));
                }
            }
        }
        return map;
    }
}