package lesson13.ExecuteManager;

import lesson13.ExecuteManager.Interface.Context;
import lesson13.ExecuteManager.Interface.ExecutionManager;

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
