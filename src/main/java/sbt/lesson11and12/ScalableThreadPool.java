package sbt.lesson11and12;


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
                .mapToObj(t -> new CycleThread())
                .collect(Collectors.toList());
        threads.forEach(Thread::start);

        state = true;
    }

    /**
     * Поместить задание в очередь
     *
     * @param runnable Задание
     */
    public void execute(Runnable runnable) {
        if (!state) {
            throw new IllegalStateException("Пул не запущен");
        }
        Objects.requireNonNull(runnable);

        synchronized (tasks) {
            if (!state) {
                throw new IllegalStateException("Пул не запущен");
            }

            tasks.add(runnable);
            tasks.notify();
            addThread();
        }
    }

    /**
     * Остановить пул потоков
     */
    public void interrupt() {
        if (!state) {
            throw new IllegalStateException("Пул уже остановлен");
        }
        synchronized (tasks) {
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
        synchronized (threads) {
            if (workThread > minThreads && tasks.size() < workThread) {
                System.out.println("ochered"+tasks.size());
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
    }

    /**
     * Добавить поток в пул потоков
     */
    private void addThread() {
        synchronized (threads) {
            if (workThread < maxThreads && tasks.size() > workThread) {
                Thread thread = new CycleThread();
                threads.add(thread);
                thread.start();
                ++workThread;
            }
        }
    }


    private class CycleThread extends Thread {

        /**
         * Запустить поток
         */
        @Override
        public void run() {
            Runnable task;
            while (true) {
                task = executeTask();
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
         * @return Задача
         */
        private Runnable executeTask() {
            Runnable task;
            try {
                synchronized (tasks) {
                    if (isInterrupted()) {
                        return null;
                    } else {
                        while (tasks.size() < 1) {
                            removeThread(Thread.currentThread());
                            tasks.wait();
                        }
                        task = tasks.poll();
                    }
                }
            } catch (InterruptedException e) {
                interrupt();
                task = null;
            }
            return task;
        }
    }
}

