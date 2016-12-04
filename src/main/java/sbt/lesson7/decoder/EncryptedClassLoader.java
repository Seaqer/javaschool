package sbt.lesson7.decoder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Артём on 13.10.2016.
 */
public class EncryptedClassLoader extends ClassLoader {
    private final byte key;
    private final File dir;

    public EncryptedClassLoader(byte key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("java") || name.equals("ru.sbt.decoder.Plugin")) {
            return super.loadClass(name);
        }
        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = name.replace('.', '/').concat(".class");
        Path path = Paths.get(dir.getPath(), className);
        byte[] classData = Decoder.decryptFile(path, key);
        return defineClass(name, classData, 0, classData.length);
    }
}

