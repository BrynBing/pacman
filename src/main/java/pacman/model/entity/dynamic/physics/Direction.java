package pacman.model.entity.dynamic.physics;

/**
 * Represents the cardinal directions allowed for movement in Pac-Man
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Retrieves the opposite direction
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    public Vector2D toVector() {
        switch (this) {
            case UP:
                return new Vector2D(0, -16);
            case DOWN:
                return new Vector2D(0, 16);
            case LEFT:
                return new Vector2D(-16, 0);
            case RIGHT:
                return new Vector2D(16, 0);
            default:
                return new Vector2D(0, 0);
        }
    }
}
