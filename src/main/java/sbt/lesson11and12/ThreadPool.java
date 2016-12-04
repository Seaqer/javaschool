package sbt.lesson11and12;

/**
 * Created by Артём on 26.10.2016.
 */
public interface ThreadPool {
    void execute(Runnable runnable);
    void interrupt();
}
