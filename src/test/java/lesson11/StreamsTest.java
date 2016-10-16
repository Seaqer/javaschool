package lesson11;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Артём on 17.10.2016.
 */
public class StreamsTest {
    List<String> list;

    @Before
    public void setUp() throws Exception {
        list = Arrays.asList("A", "AB", "ABCSD", "ABC");
    }

    @Test
    public void toMap() throws Exception {
        Map<Integer, String> mapFilter =
                Streams.of(list)
                        .toMap(d -> d.length(), d -> d);
        assertEquals("toMap fail", mapFilter.get(5), "ABCSD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void toMapException() throws Exception {
        Map<Integer, String> mapFilter =
                Streams.of(Arrays.asList("A", "B"))
                        .toMap(d -> d.length(), d -> d);
    }

    @Test
    public void filter() throws Exception {
        Map<Integer, String> mapFilter =
                Streams.of(list)
                        .filter(p -> p.length() < 2)
                        .toMap(d -> d.length(), d -> d);
        assertEquals("filter fail", 1, mapFilter.size());
    }

    @Test
    public void transform() throws Exception {
        Map<Integer, String> mapFilter =
                Streams.of(list)
                        .transform(p -> p + 1)
                        .toMap(d -> d.length(), d -> d);
        assertEquals("transform fail", mapFilter.get(2), "A1");
    }
}