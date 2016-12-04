package sbt.lesson9.cache;

import sbt.lesson9.serialization.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Артём on 05.10.2016.
 */

public class CacheHandler implements InvocationHandler {
    final static Log LOGGER = LogFactory.getLog(CacheHandler.class);
    private final Object delegate;
    private final Map<Signature, Object> cache;


    public CacheHandler(Object delegate) {
        this.delegate = delegate;
        cache = new HashMap<>();
        loadCache();
    }

    /**
     * Загрузить кэш из локальной директории пользователя
     */
    private void loadCache() {
        String directory = System.getProperty("java.io.tmpdir") + "cache";
        File filePath = Paths.get(directory).toFile();
        if (filePath.exists()) {
            String[] files = filePath.list((dir, name) -> name.endsWith(".sz"));
            if (files != null)
                for (String file : files) {
                    try {
                        Signature deserializeSignature = Serialization.deserialize(directory+'\\'+file);
                        cache.put(deserializeSignature, deserializeSignature.getResult());
                    } catch (IOException | ClassNotFoundException e) {
                        LOGGER.info(e);
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
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, IOException, ClassNotFoundException {
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
    private Object getMemoryOrFileCache(Method method, Object[] args, String directory) throws InvocationTargetException, ClassNotFoundException, IllegalAccessException, IOException {
        Signature signature = new Signature(method, getArguments(args), null);
        Object result = cache.get(signature);

        if (result == null) {
            result = getFileCache(method, args, directory);
            cache.put(signature, result);
        }
        return result;
    }

    /**
     * Закешировать результат выполнения метода в файле
     *
     * @param method        Вызываемый метод
     * @param args          Аргументы метода
     * @param custDirectory Директория для кэшированых результатов
     * @return Результат выполнения метода
     */
    private Object getFileCache(Method method, Object[] args, String custDirectory) throws IOException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Object result;
        Signature signature = new Signature(method, getArguments(args), null);
        String directory = custDirectory.isEmpty() ? System.getProperty("java.io.tmpdir") + "/cache" : custDirectory;
        File filePath = Paths.get(directory + "/" + signature.toString() + ".sz").toFile();

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
    }

    /**
     * Закешировать результат выполнения метода в памяти
     *
     * @param method Вызываемый метод
     * @param args   Аргументы метода
     * @return Результат выполнения метода
     */
    private Object getMemoryCache(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object result;

        Signature signature = new Signature(method, getArguments(args), null);
        result = cache.get(signature);

        if (result == null) {
            result = method.invoke(delegate, args);
            cache.put(signature, result);
        }
        return result;
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
}