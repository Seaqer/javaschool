package sbt.lesson4;

import java.util.*;

/**
 * Created by Артём on 20.09.16
 */
class CountMapIml<T> implements CountMap<T> {


    private final Map<T, Integer> elementData;

    CountMapIml() {
        elementData = new HashMap<>();
    }

    /**
     * Добавляет элемент в этот контейнер.
     */
    public void add(T o) {
        if (o != null) {
            elementData.merge(o,1,Integer::sum);
        }
    }

    /**
     * Возвращает количество добавлений данного элемента
     */
    public int getCount(T o) {

        return (o == null) ? 0 : elementData.get(o);
    }

    /**
     * Удаляет элемент из контейнера и возвращает количество его добавлений(до удаления)
     */
    public int remove(T o) {
        int count = 0;

        if (o != null && elementData.containsKey(o)) {
            count = elementData.get(o);

            if (count == 1) {
                elementData.remove(o);
            } else {
                elementData.put(o, count - 1);
            }
        }
        return count;
    }

    /**
     * Возвращает количество разных элементов
     */
    public int size() {
        return elementData.size();
    }

    /**
     * Добавляет все элементы из source в текущий контейнер, при совпадении ключей, суммировать значения
     */
    public void addAll(CountMap<? extends T> source) {
        if (source != null) {
            for (Map.Entry<? extends T, Integer> entry : source.toMap().entrySet()) {
                elementData.merge(entry.getKey(),entry.getValue(),Integer::sum);
            }
        }
    }

    /**
     * Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
     */
    public Map<T, Integer> toMap() {
        return new HashMap<>(elementData);
    }

    /**
     * Тот же самый контракт как и toMap(), только всю информацию записать в destination
     */
    public void toMap(Map<? super T, Integer> destination) {
        if (destination != null) {
            destination.clear();
            for (Map.Entry<T, Integer> entry : elementData.entrySet()) {
                destination.merge(entry.getKey(),entry.getValue(),Integer::sum);
            }
        }
    }
}
