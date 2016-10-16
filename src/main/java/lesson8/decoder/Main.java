package lesson8.decoder;

import java.io.File;

/**
 * Created by Артём on 13.10.2016.
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader pluginLoader = new EncryptedClassLoader((byte) 232, new File("C:/HWLESS8"), Main.class.getClassLoader());
        Class<?> cla = pluginLoader.loadClass("ru.sbt.crypto.PluginImpl");
        Plugin pl = (Plugin) cla.newInstance();
        pl.doUsefull();
    }
}


