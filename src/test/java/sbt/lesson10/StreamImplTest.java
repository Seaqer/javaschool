package sbt.lesson10;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Артём on 19.10.2016.
 */
public class StreamImplTest {
    List<String> list;

    @Before
    public void setUp() throws Exception {
        list = Arrays.asList("A", "AB", "ABCSD", "ABC");
    }

    @Test
    public void toMap() throws Exception {
        Map<Integer, String> mapFilter =
                Stream.of(list)
                        .toMap(d -> d.length(), d -> d);
        assertEquals("toMap fail", mapFilter.get(5), "ABCSD");
    }


    @Test
    public void filter() throws Exception {
        Map<Integer, String> mapFilter =
                Stream.of(list)
                        .filter(p -> p.length() < 2)
                        .toMap(d -> d.length(), d -> d);
        assertEquals("filter fail", 1, mapFilter.size());
    }

    @Test
    public void transform() throws Exception {
        Map<Integer, String> mapFilter =
                Stream.of(list)
                        .transform(p -> p + 1)
                        .toMap(d -> d.length(), d -> d);
        assertEquals("transform fail", mapFilter.get(2), "A1");
    }



}