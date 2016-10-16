package lesson4;

import java.util.*;

/**
 * Created by Артём on 21.09.16
 */
class CollectionUtils {


    /**
     * Добавляет все элементы коллекции source в коллекцию destination
     */
    static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    /**
     * Создает новый массив
     */
    static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Возвращает индекс вхождения элемента o в массив source
     */
    static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    /**
     * Обрезает коллекцию до размера size
     */
    static <T> List<T> limit(List<? extends T> source, int size) {
        if (source == null) {
            throw new NullPointerException("source is null");
        }
        return new ArrayList<>(source.subList(0, size));
    }

    /**
     * Добавлет элемент в коллекцию
     */
    static <T> void add(List<T> source, T o) {
        source.add(o);
    }

    /**
     * Удаляет все вхождения элементов в коллекции removeFrom из коллекции c2
     */
    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        if (removeFrom == null || c2 == null) {
            return;
        }
        for (T element : c2) {
            removeFrom.remove(element);
        }


    }

    /**
     * Возвращает все вхождения элементов в коллекции с1 из коллекции c2
     */
    static <T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        boolean result = true;

        if (c1 == null && c2 == null) {
            result = false;
        } else {
            for (int i = 0; i < c2.size(); i++) {
                if (!c1.contains(c2.get(i))) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
     */
    static <T> boolean containsAny(List<? extends T> c1, List<? super T> c2) {
        boolean result = false;

        if (c1 != null || c2 != null) {
            for (int i = 0; i < c2.size(); i++) {
                if (c1.contains(c2.get(i))) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
     */
    static <T extends Comparable<? super T>> List<T> range(List<? extends T> list, T min, T max) {
        List<T> result = new ArrayList<>();

        if (list != null && min != null && max != null) {
            for (T element : list) {
                if (element.compareTo(min) > 0 && element.compareTo(max) < 0) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    /**
     * Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
     * Сравнение элементов через Comparable
     */
    static <T> List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        List<T> result = new ArrayList<>();

        if (list != null && min != null && max != null) {
            for (T element : list) {
                if (comparator.compare(element, min) > 0 && comparator.compare(element, max) < 0) {
                    result.add(element);
                }
            }
        }
        return result;
    }
}






