package sbt.lesson16goodcode.task2;

public enum Orientation {

    NORTH {
        @Override
        public Point move(Point currentPosition) {
            return new Point(currentPosition.getX(), currentPosition.getY() + 1);
        }

        @Override
        public Orientation turn() {
            return Orientation.EAST;
        }
    }, WEST {
        @Override
        public Point move(Point currentPosition) {
            return new Point(currentPosition.getX() - 1, currentPosition.getY());
        }

        @Override
        public Orientation turn() {
            return Orientation.NORTH;
        }
    }, SOUTH {
        @Override
        public Point move(Point currentPosition) {
            return new Point(currentPosition.getX(), currentPosition.getY() - 1);
        }

        @Override
        public Orientation turn() {
            return Orientation.WEST;
        }
    }, EAST {
        @Override
        public Point move(Point currentPosition) {
            return new Point(currentPosition.getX() + 1, currentPosition.getY());
        }

        @Override
        public Orientation turn() {
            return Orientation.SOUTH;
        }
    };

    /**
     * Сделать шаг вперед
     * @param point текушая позиция
     * @return новая позиция
     */
    public abstract Point move(Point point);

    /**
     * Сменить направление
     * @return новое направление
     */
    public abstract Orientation turn();

}
