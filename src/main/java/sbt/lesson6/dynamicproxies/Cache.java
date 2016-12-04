package sbt.lesson6.dynamicproxies;

import java.lang.annotation.*;

/**
 * Created by Артём on 05.10.2016.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
}