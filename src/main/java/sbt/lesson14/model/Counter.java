package sbt.lesson14.model;

import sbt.lesson14.cache.Cache;
import sbt.lesson14.cache.CacheType;

/**
 * Created by Артём on 05.10.2016.
 */
public interface Counter {

    @Cache(getCacheType = CacheType.MEMORY)
    int getCountWords(int length, String string);

    @Cache(getCacheType = CacheType.MEMORY_AND_FILE, getPath = "")
    int getCountAllWords(String string);
}
