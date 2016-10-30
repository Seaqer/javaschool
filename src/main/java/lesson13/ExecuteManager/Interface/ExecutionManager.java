package lesson13.ExecuteManager.Interface;

/**
 * Created by Артём on 29.10.2016.
 */
public interface ExecutionManager {
    /**
     * Получения контекста ExecutionManager
     * @param callback Завершающая задача
     * @param tasks Массив задач
     * @return Контекст задачи
     */
    Context execute(Runnable callback, Runnable... tasks);
}

