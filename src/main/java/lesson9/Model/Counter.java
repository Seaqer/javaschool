package lesson9.Model;

import lesson9.Cache.Cache;
import lesson9.Cache.CacheType;

/**
 * Created by Артём on 05.10.2016.
 */
public interface Counter {

    @Cache(getCacheType = CacheType.MEMORY_AND_FILE, getPath = "")
    int getCountWords(int length, String string);

    @Cache(getCacheType = CacheType.MEMORY)
    int getCountAllWords(String string);
}
