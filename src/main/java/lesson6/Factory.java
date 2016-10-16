package lesson6;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.io.File;
import java.util.stream.Collectors;

/**
 * Created by svetlana on 25.09.16.
 */
public class Factory implements Factorable {

    private final Package basePackage;
    private Map<Class<?>, List<Object>> beans = new HashMap<>();

    private Factory(Class<?> markerClass) {
        this.basePackage = markerClass.getPackage();
    }

    public static Factory createNew(Class<?> cls) {
        Factory f = new Factory(cls);
        try {
            f.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    public <T> T getBean(Class<T> cls) {
        final List<Object> candidates = beans.getOrDefault(cls, Collections.emptyList());
        if (candidates.isEmpty()) {
            throw new RuntimeException("There is not candidate!");
        }
        if (candidates.size() > 1) {
            throw new RuntimeException("There are more than one candidate: " + candidates.toString());
        }
        return cls.cast(candidates.get(0));
    }

    private void init() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        final File baseFile = new File(Thread.currentThread()
                .getContextClassLoader()
                .getResource(basePackage.getName().replace(".", "/"))
                .getFile());

        final List<Class<?>> classes = getAllClassesByPredicate(baseFile, c -> {
            if (c.isAnnotationPresent(Component.class)) {
                if (Modifier.isInterface(c.getModifiers()) || Modifier.isAbstract(c.getModifiers())) {
                    throw new RuntimeException(c + " should not be abstract or interface");
                } else {
                    return true;
                }
            } else {
                return false;
            }
        });

        final Map<Class<?>, List<Class<?>>> beansTypes = obtainGraph(classes);

        for (Class<?> c : classes) {
            if (!beans.containsKey(c)) {
                createObjectAndReg(c, beansTypes);
            }
        }

        for (Map.Entry<Class<?>, List<Object>> entry : beans.entrySet()) {
            for (Object bean : entry.getValue()) {
                invokeAnnotationMethods(entry.getKey(), bean, PreDestroy.class);
            }
        }
    }

    private Map<Class<?>, List<Class<?>>> obtainGraph(List<Class<?>> classes) {
        final Map<Class<?>, List<Class<?>>> result = new HashMap<>();
        for (Class<?> orig : classes) {
            result.put(orig, obtainGraphHelper(orig));
        }
        return result;
    }

    private List<Class<?>> obtainGraphHelper(Class<?> c) {
        List<Class<?>> result = new ArrayList<>();
        obtainGraphHelper2(c, result);
        return result;

    }

    private void obtainGraphHelper2(Class<?> c, List<Class<?>> result) {
        Class<?> superCls = c.getSuperclass();
        Class<?> intrf[] = c.getInterfaces();

        if (superCls != null) {
            obtainGraphHelper2(superCls, result);
            result.add(superCls);
        }
        Arrays.stream(intrf).forEach(result::add);
        for (Class<?> i : intrf) {
            obtainGraphHelper2(i, result);
        }
    }

    private void createObjectAndReg(Class<?> beanCls, Map<Class<?>, List<Class<?>>> beansTypes)
            throws IllegalAccessException, InstantiationException {

        final Object bean = beanCls.newInstance();
        registerBean(bean, beanCls, beansTypes.get(beanCls));

        for (Field f : getAllDependsFor(beanCls)) {
            Class<?> depBeanCls = findBeanClsFor(f.getType(), beansTypes);
            if (!beans.containsKey(depBeanCls)) {
                createObjectAndReg(depBeanCls, beansTypes);
            }
            setDepend(bean, f);
        }
    }

    private void registerBean(Object bean, Class<?> beanCls, List<Class<?>> classes) {
        registerBean(bean, beanCls);
        classes.forEach(c -> registerBean(bean, c));
    }

    private void registerBean(Object bean, Class<?> cls) {
        List<Object> cur = beans.get(cls);
        if (cur == null) {
            cur = new ArrayList<>();
            beans.put(cls, cur);
        }
        cur.add(bean);
    }

    private Class<?> findBeanClsFor(Class<?> d, Map<Class<?>, List<Class<?>>> beansTypes) {
        List<Class<?>> result = new ArrayList<>();
        if (beansTypes.containsKey(d)) {
            result.add(d);
        }
        for (Map.Entry<Class<?>, List<Class<?>>> i : beansTypes.entrySet()) {
            i.getValue().stream().filter(e -> e.equals(d)).findAny()
                    .ifPresent(e -> result.add(i.getKey()));
        }
        if (result.size() > 1) {
            throw new RuntimeException("More than one candidate have been found!");
        }
        if (result.isEmpty()) {
            throw new RuntimeException("Could not find dependency: " + d.toString());
        }
        return result.get(0);
    }

    private List<Field> getAllDependsFor(Class<?> c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toList());
    }

    private List<Class<?>> getAllClassesByPredicate(File basePath, Predicate<Class<?>> p) throws IOException, ClassNotFoundException {
        return Files.find(basePath.toPath(), Integer.MAX_VALUE,
                (path, attr) -> path.toString().endsWith(".class"))
                .map(asStringOfCLass(basePath))
                .map(toClass())
                .filter(p)
                .collect(Collectors.toList());

    }

    private Function<? super String, ? extends Class<?>> toClass() {
        return s -> {
            try {
                return Class.forName(s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private Function<? super Path, ? extends String> asStringOfCLass(File basePath) {
        return path -> basePackage.getName() + "." + path.toString()
                .substring(basePath.toString().length())
                .replace(".class", "")
                .replace(File.separator, ".").substring(1);
    }

    private void setDepend(Object bean, Field f) throws IllegalAccessException {
        Class<?> fc = f.getType();
        List<Object> dep = beans.get(fc);
        f.setAccessible(true);
        f.set(bean, dep.get(0));
        f.setAccessible(false);

    }

    private void invokeAnnotationMethods(Class<?> beanCls, Object bean, Class<? extends Annotation> annotationClass) {
        try {
            for (Method method : beanCls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotationClass)) {
                    method.setAccessible(true);
                    method.invoke(bean);
                    method.setAccessible(false);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        //TODO: PreDestroy
        for (Map.Entry<Class<?>, List<Object>> entry : beans.entrySet()) {
            for (Object bean : entry.getValue()) {
                invokeAnnotationMethods(entry.getKey(), bean, PreDestroy.class);
            }
        }
    }
}
