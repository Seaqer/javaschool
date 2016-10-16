package lesson8.browser;


public class Main {

    public static void main(String[] args) {
        PluginManager manager = new PluginManager("file://C:/HWLESS8/out/pluginRootDirectory");
        Plugin superPlugin = manager.load("myplugin", "myplg");
        superPlugin.doUsefull();
    }
}
