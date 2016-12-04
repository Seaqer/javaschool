package sbt.lesson16goodcode.task2;

public class Field {
    private final int width;
    private final int height;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean inDitch(Point point) {
        return point.getX() > width || point.getY() > height;
    }
}
