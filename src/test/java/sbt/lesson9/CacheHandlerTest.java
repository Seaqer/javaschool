package sbt.lesson9;

import sbt.lesson9.cache.CacheHandler;
import sbt.lesson9.model.Counter;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.mockito.Mockito.*;


/**
 * Created by Артём on 24.10.2016.
 */
public class CacheHandlerTest {

    Counter counter;

    @Before
    public void setUp() throws Exception {
        counter = mock(Counter.class);
    }

    @Test
    public void testCache() throws Throwable {

        Counter counterCache = (Counter) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{Counter.class},
                new CacheHandler(counter)
        );
        counterCache.getCountAllWords("When plans for its replacement by a modern steelworks");
        verify(counter).getCountAllWords("When plans for its replacement by a modern steelworks");
        counterCache.getCountAllWords("When plans for its replacement by a modern steelworks");
        verifyNoMoreInteractions(counter);
    }
}