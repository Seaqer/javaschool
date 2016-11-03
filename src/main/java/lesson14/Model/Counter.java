package lesson14.Model;

import lesson14.Cache.Cache;
import lesson14.Cache.CacheType;

/**
 * Created by Артём on 05.10.2016.
 */
public interface Counter {

    @Cache(getCacheType = CacheType.MEMORY)
    int getCountWords(int length, String string);

    @Cache(getCacheType = CacheType.MEMORY_AND_FILE, getPath = "")
    int getCountAllWords(String string);
}
