package sbt.lesson13.executemanager;

import sbt.lesson13.executemanager.interfaces.Context;
import sbt.lesson13.executemanager.interfaces.ExecutionManager;

/**
 * Created by Артём on 29.10.2016.
 */
public class ExecutionManagerImpl implements ExecutionManager {

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        ThreadPool pool = new ThreadPool(10);
        pool.setCallback(callback);
        for (Runnable task : tasks) {
            pool.execute(task);
        }
        pool.start();
        return pool.getContext();
    }
}
