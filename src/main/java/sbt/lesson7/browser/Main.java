package sbt.lesson7.browser;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        PluginManager manager = new PluginManager("file://C:/HWLESS8/out/pluginRootDirectory");
        Plugin superPlugin = manager.load("myplugin", "myplg");
        superPlugin.doUsefull();
    }
}
