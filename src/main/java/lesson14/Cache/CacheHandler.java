package lesson14.Cache;

import lesson14.Serialization.Serialization;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Артём on 05.10.2016.
 */

public class CacheHandler implements InvocationHandler {

    private final Object delegate;
    private final Map<Signature, Object> cache;
    private final Map<Signature, Lock> locks;


    public CacheHandler(Object delegate) {
        this.delegate = delegate;
        cache = new ConcurrentHashMap<>();
        locks = new ConcurrentHashMap<>();
        loadCache();
    }

    /**
     * Загрузить кэш из локальной директории пользователя
     */
    private void loadCache() {
        String directory = System.getProperty("java.io.tmpdir") + "Cache";
        File filePath = Paths.get(directory).toFile();
        if (filePath.exists()) {
            String[] files = filePath.list((dir, name) -> name.endsWith(".sz"));
            if (files != null)
                for (String file : files) {
                    try {
                        Signature deserializeSignature = Serialization.deserialize(directory + '\\' + file);
                        cache.put(deserializeSignature, deserializeSignature.getResult());
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
        }

    }

    /**
     * Вызвать кэширующий метод
     *
     * @param proxy  Прокси
     * @param method Вывзываемый метод
     * @param args   Аргументы вызываемого метода
     * @return Результат вызываемого метода
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method == null) {
            throw new NullPointerException();
        }

        if (!method.isAnnotationPresent(Cache.class)) {
            result = method.invoke(delegate, args);
        } else {
            Cache annotations = method.getDeclaredAnnotation(Cache.class);

            switch (annotations.getCacheType()) {
                case MEMORY:
                    result = getMemoryCache(method, args);
                    break;
                case FILE:
                    result = getFileCache(method, args, annotations.getPath());
                    break;
                case MEMORY_AND_FILE:
                    result = getMemoryOrFileCache(method, args, annotations.getPath());
                    break;
            }
        }
        return result;
    }

    /**
     * Закешировать результат метода в памяти или в файле
     *
     * @param method    Вызываемый метод
     * @param args      Аргументы метода
     * @param directory Директория для кэшированых результатов
     * @return Результат выполнения метода
     * @throws Throwable
     */
    private Object getMemoryOrFileCache(Method method, Object[] args, String directory) throws Throwable {
        Object result;
        Signature signature = new Signature(method, getArguments(args), null);
        Lock lock = getLock(signature);
        try {
            lock.lock();
            if (!cache.containsKey(signature)) {

                result = getFileCache(method, args, directory);
                cache.put(signature, result);
            } else {
                result = cache.get(signature);
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Закешировать результат выполнения метода в файле
     *
     * @param method        Вызываемый метод
     * @param args          Аргументы метода
     * @param custDirectory Директория для кэшированых результатов
     * @return Результат выполнения метода
     * @throws Throwable
     */
    private Object getFileCache(Method method, Object[] args, String custDirectory) throws Throwable {
        Object result;
        Signature signature = new Signature(method, getArguments(args), null);
        String directory = custDirectory.isEmpty() ? System.getProperty("java.io.tmpdir") + "/Cache" : custDirectory;
        File filePath = Paths.get(directory + "/" + signature.toString() + ".sz").toFile();
        final Lock lock = getLock(signature);
        try {
            lock.lock();
            if (!filePath.exists()) {

                if (Files.notExists(Paths.get(directory))) {
                    Files.createDirectory(Paths.get(directory)).toFile();
                }

                result = method.invoke(delegate, args);
                signature.setResult(result);
                Serialization.serialize(filePath.getPath(), signature);
            } else {
                Signature deserializeSignature = Serialization.deserialize(filePath.getPath());

                if (signature.equals(deserializeSignature)) {
                    result = deserializeSignature.getResult();
                } else {
                    result = method.invoke(delegate, args);
                }
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Закешировать результат выполнения метода в памяти
     *
     * @param method Вызываемый метод
     * @param args   Аргументы метода
     * @return Результат выполнения метода
     * @throws Throwable
     */
    private Object getMemoryCache(Method method, Object[] args) throws Throwable {
        Object result;
        Signature signature = new Signature(method, getArguments(args), null);
        final Lock lock = getLock(signature);

        try {
            lock.lock();
            result = cache.get(signature);

            if (result == null) {
                result = method.invoke(delegate, args);
                cache.put(signature, result);
            }
            return result;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Получение тексового представления аргуентов
     *
     * @param args Аргументы
     * @return Текстовое представление аргументов
     */
    private String getArguments(Object[] args) {

        StringBuilder arguments = new StringBuilder();

        for (Object arg : args) {
            arguments.append(arg.toString());
        }
        return arguments.toString();
    }

    private Lock getLock(Signature signature) {
        locks.putIfAbsent(signature, new ReentrantLock());
        return locks.get(signature);
    }
}