package lesson11and12;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Артём on 26.10.2016.
 */
public class ScalableThreadPool implements ThreadPool {
    private final List<Thread> threads;
    private final Queue<Runnable> tasks;
    private final int minThreads;
    private final int maxThreads;
    private volatile int workThread;
    private volatile boolean state;

    public ScalableThreadPool(int minThreads, int maxThreads) {
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        workThread = minThreads;
        tasks = new ArrayDeque<>();

        threads = IntStream.range(0, minThreads)
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

    /**
     * Удалить поток из пула
     *
     * @param thread Поток
     */
    private void removeThread(Thread thread) {
        if (workThread > minThreads && tasks.size() != workThread) {
            Iterator<Thread> iterator = threads.listIterator();
            while (iterator.hasNext()) {
                Thread curentThread = iterator.next();
                if (curentThread.equals(thread)) {
                    curentThread.interrupt();
                    iterator.remove();
                }
            }
            --workThread;
            thread.interrupt();
        }
    }

    /**
     * Добавить поток в пул потоков
     */
    private void addThread() {
        if (workThread < maxThreads && tasks.size() != workThread) {
            Thread thread = new cycleThread();
            threads.add(thread);
            thread.start();
            ++workThread;
        }
    }

    /**
     * Проверка нужности создания/удаления потока
     *
     * @param thread
     */
    private void checkCountThread(Thread thread) {
        synchronized (tasks) {
            if (tasks.size() < workThread) {
                removeThread(thread);
            } else {
                addThread();
            }
        }
    }


    private class cycleThread extends Thread {

        /**
         * Запустить поток
         */
        @Override
        public void run() {
            Runnable task;
            while (true) {
                task = executeTask();
                checkCountThread(Thread.currentThread());
                if (task == null || isInterrupted()) {
                    interrupt();
                    return;
                }
                task.run();
            }
        }

        /**
         * Ожидание потоком задания из очереди
         *
         * @return
         */
        private Runnable executeTask() {
            Runnable task;
            try {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        tasks.wait();
                    }
                    task = tasks.poll();
                }
            } catch (InterruptedException e) {
                task = null;
            }
            return task;
        }
    }
}

