package lesson11;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Артём on 16.10.2016.
 */
public class Streams<T> {

    private final List<T> list;

    private Streams(List<T> list) {
        this.list = list;
    }

    /**
     * Метод для создания объекта Streams из коллекции
     *
     * @param list Коллекция элементов
     * @param <T>  Тип объектов коллеции
     * @return новый объект Streams
     */
    public static <T> Streams<T> of(List<T> list) {
        if (list == null) {
            throw new NullPointerException("in of method");
        }
        return new Streams<>(new ArrayList<>(list));
    }

    /**
     * Метод фильтрующий список в соотвествии с условием предиката.
     *
     * @param predicate Условие фильтрации
     * @return Отфильтрованный объект Streams
     */
    public Streams<T> filter(Predicate<? super T> predicate) {
        if (predicate == null) {
            throw new NullPointerException("in filter method");
        }

        Iterator<T> iterator = list.listIterator();

        while (iterator.hasNext()) {
            T element = iterator.next();
            if (predicate.negate().test(element)) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Метод преобразующий элемент одного типа в элемент другого типа
     *
     * @param transform Функця преобразования
     * @param <E>       Новый тип объектов коллекции
     * @return Преобразованный объект Streams
     */
    public <E> Streams<E> transform(Function<T, ? extends E> transform) {
        if (transform == null) {
            throw new NullPointerException("in transform method");
        }
        List<E> transformList = new ArrayList<>();

        for (T element : list) {
            E elementNew = transform.apply(element);
            transformList.add(elementNew);
        }
        return new Streams<>(transformList);
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
    public <K, V> Map<K, V> toMap(Function<T, ? extends K> keyFunction, Function<T, ? extends V> valueFunction) {
        if (keyFunction == null || valueFunction == null) {
            throw new NullPointerException("in toMap method");
        }
        HashMap<K, V> map = new HashMap<>();

        for (T element : list) {
            K key = keyFunction.apply(element);
            V value = valueFunction.apply(element);
            V valueMap = map.get(key);

            if (valueMap != null) {
                if (!valueMap.equals(value)) {
                    throw new IllegalArgumentException("Создание элемента: по существующему ключу" + key.toString());
                }
            } else {
                map.put(key, value);
            }
        }
        return map;
    }
}

