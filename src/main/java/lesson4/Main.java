package lesson4;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        String resultCM = TestCountMap();
        String resultCU = TestCollectionUtils();

        System.out.println("Тестирование Count Map");
        System.out.println((resultCM == null) ? "Тестирование прошло успешно" : resultCM);

        System.out.println("Тестирование Collection Utils");
        System.out.println((resultCU == null) ? "Тестирование прошло успешно" : resultCU);

    }

    private static String TestCollectionUtils() {

        List<Integer> dst = Arrays.asList(1, 2, 3);
        List<Number> src = CollectionUtils.newArrayList();

        CollectionUtils.add(src, 2);
        if (src.size() < 1) {
            return "Error: CollectionUtils.add(src,2)";
        }

        if (!CollectionUtils.containsAny(dst, src)) {
            return "Error: CollectionUtils.containsAny(dst,src)";
        }

        if (!CollectionUtils.containsAll(dst, src)) {
            return "Error: CollectionUtils.containsAny(dst,src)";
        }

        if (CollectionUtils.indexOf(dst, 2) < 0) {
            return "Error: CollectionUtils.indexOf(dst,2)";
        }

        if (CollectionUtils.limit(dst, 2).size() == 2) {
            return "Error: CollectionUtils.indexOf(dst,2)";
        }

        if (CollectionUtils.range(dst, 1, 3).get(0) != 2) {
            return "Error: CollectionUtils.range(dst,1,3)";
        }

        List<Integer> result = CollectionUtils.range(dst, 1, 3, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        if (result.get(0) != 2) {
            return "Error: CollectionUtils.range(dst,1,3)";
        }
        return null;
    }

    private static String TestCountMap() {
        CountMapIml<String> src = new CountMapIml<>();
        CountMapIml<String> trg = new CountMapIml<>();
        Map<String, Integer> mapTrg = new HashMap<>();

        trg.add("7");
        if (trg.getCount("7") != 1) {
            return "Error: trg.add(\"7\")";
        }

        trg.remove("7");
        if (trg.size() != 0) {
            return "Error: trg.remove(\"7\")";
        }

        trg.add("1");
        trg.add("1");
        if (trg.getCount("1") != 2) {
            return "Error: double add trg.add(\"1\")";
        }

        src.add("3");
        src.add("3");
        src.add("1");
        trg.addAll(src);
        if (src.getCount("1") != 3 && src.getCount("3") != 2) {
            return "Error: trg.addAll(src)";
        }

        trg.toMap(mapTrg);
        if (mapTrg.get("1") != 3 && mapTrg.get("3") != 2) {
            return "Error: trg.toMap(mapTrg)";
        }
        return null;
    }
}
