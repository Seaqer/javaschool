package sbt.lesson13.executemanager;

import sbt.lesson13.executemanager.interfaces.Context;
import sbt.lesson11and12.FixedThreadPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Артём on 29.10.2016.
 */
public class ThreadPool extends FixedThreadPool {
    final static Log LOGGER = LogFactory.getLog(ThreadPool.class);
    private volatile int completedTasks;
    private volatile int failedTasks;
    private volatile int interruptedTasks;
    private volatile int countTasks;
    private volatile boolean interrupt;
    private volatile Context context;
    private final Object lock = new Object();

    public ThreadPool(int countThreads) {
        super(countThreads);
        completedTasks = 0;
        failedTasks = 0;
        interruptedTasks = 0;
        interrupt = false;
        context = new PoolContext();
    }

    /**
     * Поместить задание в очередь
     *
     * @param runnable Задание
     */
    @Override
    public void execute(Runnable runnable) {
        ++countTasks;
        super.execute(() -> {
            try {
                if (interrupt) {
                    synchronized (lock) {
                        interruptedTasks++;
                    }
                    return;
                }
                runnable.run();
                synchronized (lock) {
                    completedTasks++;
                }
            } catch (Exception e) {
                synchronized (lock) {
                    failedTasks++;
                }
                LOGGER.info(e);
            } finally {
                countDown();
            }
        });
    }

    /**
     * Установить callback метод для пула
     *
     * @param runnable Метод
     */
    public void setCallback(Runnable runnable) {
        new Thread(() -> {
            try {
                synchronized (lock) {
                    while (!context.isFinished()){
                        lock.wait();
                    }
                }
                runnable.run();
            } catch (InterruptedException e) {
                LOGGER.info(e);
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Запустить callback
     */
    private void countDown() {
        synchronized (lock) {
            if (context.isFinished()) {
                lock.notify();
            }
        }
    }

    /**
     * Вернуть контекст пула потоков.
     *
     * @return
     */
    public Context getContext() {
        return context;
    }


    private class PoolContext implements Context {
        @Override
        public int getCompletedTaskCount() {
            return completedTasks;
        }

        @Override
        public int getFailedTaskCount() {
            return failedTasks;
        }

        @Override
        public int getInterruptedTaskCount() {
            return interruptedTasks;
        }

        @Override
        public void interrupt() {
            interrupt = true;
        }

        @Override
        public boolean isFinished() {
            return failedTasks + interruptedTasks + completedTasks == countTasks;
        }
    }
}
