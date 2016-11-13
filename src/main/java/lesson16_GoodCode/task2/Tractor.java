package lesson16_GoodCode.task2;;

import lesson16_GoodCode.task2.exception.TractorInDitchException;

public class Tractor {

    Point position;
    Orientation orientation;
    Field field;

    public Tractor() {
        position = new Point(0, 0);
        orientation = Orientation.NORTH;
        field = new Field(5, 5);
    }

    /**
     * Выполнить действие
     *
     * @param command выбираемое действие
     */
    public void move(String command) {
        if (command.equals("F")) {
            moveForwards();
        }
        if (command.equals("T")) {
            turnClockwise();
        }
    }

    /**
     * Сделать шаг вперед
     */
    public void moveForwards() {
        position = orientation.move(position);
        if (field.inDitch(position)) {
            throw new TractorInDitchException();
        }
    }

    /**
     * Поворот по часовой стрелке
     */
    public void turnClockwise() {
        orientation = orientation.turn();
    }

    /**
     * Получить координату X
     */
    public int getPositionX() {
        return position.getX();
    }

    /**
     * Получить координату Y
     */
    public int getPositionY() {
        return position.getY();
    }

    /**
     * Получить текущее направление
     */
    public Orientation getOrientation() {
        return orientation;
    }

}