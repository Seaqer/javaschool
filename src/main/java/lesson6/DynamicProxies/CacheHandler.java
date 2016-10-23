package lesson6.DynamicProxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Артём on 05.10.2016.
 */

public class CacheHandler implements InvocationHandler {

    private final Object delegate;
    private final Map<Signature, Object> cache;

    public CacheHandler(Object delegate) {
        this.delegate = delegate;
        cache = new HashMap<>();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;

        if (method == null || args == null) {
            throw new NullPointerException();
        }

        if (!method.isAnnotationPresent(Cache.class)) {
            result = method.invoke(delegate, args);
        } else {
            StringBuilder arguments = new StringBuilder();

            for (Object arg : args) {
                arguments.append(arg.toString());
            }

            Signature signature = new Signature(method, arguments.toString());
            result = cache.get(signature);

            if (result == null) {
                result = method.invoke(delegate, args);
                cache.put(signature, result);
            }
        }
        return result;
    }
}