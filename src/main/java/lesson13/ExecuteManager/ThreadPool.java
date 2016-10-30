package lesson13.ExecuteManager;

import lesson13.ExecuteManager.Interface.Context;
import lesson11and12.FixedThreadPool;

/**
 * Created by Артём on 29.10.2016.
 */
public class ThreadPool extends FixedThreadPool {
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
                    lock.wait();
                }
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                interrupt();
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
