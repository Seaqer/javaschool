package sbt.lesson11and12;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Артём on 25.10.2016.
 */
public class FixedThreadPool implements ThreadPool {
    final static Log LOGGER = LogFactory.getLog(FixedThreadPool.class);
    private final List<Thread> threads;
    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private volatile boolean state;

    public FixedThreadPool(int countPool) {
        threads = IntStream.range(0, countPool)
                .mapToObj(t -> new CycleThread())
                .collect(Collectors.toList());
        state = false;
    }

    /**
     * Запустить потоки из пула потоков
     */
    public void start() {
        if (state) {
            throw new IllegalStateException("Пул уже запущен");
        }
        synchronized (this) {
            threads.forEach(Thread::start);
            state = true;
        }
    }

    /**
     * Поместить задание в очередь
     *
     * @param runnable Задание
     */
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notify();
        }
    }

    /**
     * Остановить пул потоков
     */
    public void interrupt() {
        if (!state) {
            throw new IllegalStateException("Пул уже остановлен");
        }
        synchronized (this) {
            threads.forEach(Thread::interrupt);
            state = false;
        }
    }


    private class CycleThread extends Thread {

        /**
         * Запустить поток
         */
        @Override
        public void run() {
            while (true) {
                try {
                    executeTask().run();
                } catch (InterruptedException e) {
                    LOGGER.info(e);
                    currentThread().interrupt();
                    return;
                }
            }
        }


        /**
         * Ожидание потоком задания из очереди
         *
         * @return задача
         */
        private Runnable executeTask() throws InterruptedException {
            synchronized (tasks) {
                while (tasks.isEmpty()) {
                    tasks.wait();
                }
                return tasks.poll();
            }
        }
    }
}
