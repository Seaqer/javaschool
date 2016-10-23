package lesson10;

@FunctionalInterface
interface PipelineAction<T, R> {
    R run(T element);
}