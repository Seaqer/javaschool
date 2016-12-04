package sbt.lesson13.mytask;

/**
 * Created by Артём on 29.10.2016.
 */
public class TaskExecuteException extends RuntimeException {
    public TaskExecuteException(String message, Exception e) {
        super(message, e);
    }
}
