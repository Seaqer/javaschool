package lesson9.Cache;

import java.lang.annotation.*;

/**
 * Created by Артём on 05.10.2016.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    CacheType getCacheType() default CacheType.MEMORY;
    String getPath() default "";
}