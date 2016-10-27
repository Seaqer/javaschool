package lesson11and12;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Артём on 25.10.2016.
 */
public class FixedThreadPool implements ThreadPool {
    private final List<Thread> threads;
    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private final Object lock = new Object();
    private volatile boolean state;

    public FixedThreadPool(int countPool) {
        threads = IntStream.range(0, countPool)
                .mapToObj(t -> new cycleThread())
                .collect(Collectors.toList());
        state = false;
    }

    /**
     * Запустить потоки из пула потоков
     */
    public void start() {
        if (state) {
            throw new RuntimeException("Пул уже запущен");
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
        if (!state) {
            throw new RuntimeException("Пул не запущен");
        }
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
            throw new RuntimeException("Пул уже остановлен");
        }
        synchronized (this) {
            threads.forEach(Thread::interrupt);
            state = false;
        }
    }


    private class cycleThread extends Thread {

        /**
         * Запустить поток
         */
        @Override
        public void run() {
            while (true) {
                executeTask().run();
            }
        }




        /**
         * Ожидание потоком задания из очереди
         *
         * @return задача
         */
        private Runnable executeTask() {
            try {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        tasks.wait();
                    }
                    return tasks.poll();
                }
            } catch (InterruptedException e) {
                interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
