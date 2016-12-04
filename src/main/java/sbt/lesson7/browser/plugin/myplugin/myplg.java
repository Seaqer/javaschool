package sbt.lesson7.browser.plugin.myplugin;

import  sbt.lesson7.browser.Plugin;

public class myplg implements Plugin {
    @Override
    public void doUsefull() {
        System.out.println("MyPlugin doUsefull 1");
        new Trace().traceConsole();
    }
}