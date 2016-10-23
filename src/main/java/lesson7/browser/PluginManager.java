package lesson7.browser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Артём on 08.10.2016.
 */
public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {

        try {
            URL path = new URL(pluginRootDirectory + "/" + pluginName + "/");
            PluginLoader pluginLoader = new PluginLoader(new URL[]{path});
            return (Plugin) pluginLoader.loadClass(pluginClassName).newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Класс плагина не найден");
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не возможно построить класс плагина");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Не удается распознать путь");
        }
    }
}
