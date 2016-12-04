package sbt.lesson7.browser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Артём on 08.10.2016.
 */
public class PluginManager {
    private final String pluginRootDirectory;
    final static Log LOGGER = LogFactory.getLog(PluginManager.class);

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        Plugin plugin;
        try {
            URL path = new URL(pluginRootDirectory + "/" + pluginName + "/");
            PluginLoader pluginLoader= new PluginLoader(new URL[]{path});
            plugin = (Plugin) pluginLoader.loadClass(pluginClassName).newInstance();
            pluginLoader.close();
            return plugin;
        } catch (ClassNotFoundException e) {
            LOGGER.info(e);
            throw new ClassNotFoundException("Класс плагина не найден",e);
        } catch (MalformedURLException e) {
            LOGGER.info(e);
            throw new MalformedURLException("Не удается распознать путь");
        }catch (IOException e) {
            LOGGER.info(e);
            throw new IOException("Ошибки чтения файла");
        }
    }
}
