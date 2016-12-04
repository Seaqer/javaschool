package sbt.lesson13.mytask;

import java.util.concurrent.Callable;

/**
 * Created by Артём on 29.10.2016.
 */
public class Task<T> {
    private volatile T value;
    private volatile TaskExecuteException exception;
    private final Callable<? extends T> callable;
    private final Object lock;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
        lock = new Object();
    }

    /**
     * Получть результат задачи
     *
     * @return Результат задачи
     */
    public T get() {
        if (validateParametrs()) {
            synchronized (lock) {
                if (validateParametrs()) {
                    try {
                        value = callable.call();
                    } catch (Exception e) {
                        exception = new TaskExecuteException("Нет возможности вычислить результат", e);
                        throw exception;
                    }
                }
            }
        }
        return value;
    }

    /**
     * Проверка праметров
     *
     * @return результат
     */
    private boolean validateParametrs() {

        if (exception != null) {
            throw exception;
        }
        return value == null;
    }
}

