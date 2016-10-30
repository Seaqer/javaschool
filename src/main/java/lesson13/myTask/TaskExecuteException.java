package lesson13.myTask;

/**
 * Created by Артём on 29.10.2016.
 */
public class TaskExecuteException extends RuntimeException {
    public TaskExecuteException(String message, Exception e) {
        super(message, e);
    }
}
