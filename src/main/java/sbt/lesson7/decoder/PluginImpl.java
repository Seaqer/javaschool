package sbt.lesson7.decoder;

/**
 * Created by Артём on 13.10.2016.
 */
public class PluginImpl implements Plugin {
    @Override
    public void doUsefull() {
        System.out.println("i'm PluginImpl");
    }
}
