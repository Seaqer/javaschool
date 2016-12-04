package sbt.lesson9.model;

import sbt.lesson9.cache.Cache;
import sbt.lesson9.cache.CacheType;

/**
 * Created by Артём on 05.10.2016.
 */
public interface Counter {

    @Cache(getCacheType = CacheType.MEMORY_AND_FILE, getPath = "")
    int getCountWords(int length, String string);

    @Cache(getCacheType = CacheType.MEMORY)
    int getCountAllWords(String string);
}
