package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

import static pacman.model.entity.dynamic.physics.Vector2D.calculateEuclideanDistance;

public class ClydeChaseStrategy implements ChaseStrategy {
    private static final int LAZY_DISTINCTION = 8 * 16;
    private static final int BOTTOM_Y_POSITION_OF_MAP = 16 * 34;
    private static final Vector2D targetCorner = new Vector2D(0, BOTTOM_Y_POSITION_OF_MAP);

    @Override
    public Vector2D chase(Ghost ghost, Vector2D playerPosition, Direction playerDirection) {
        if (calculateEuclideanDistance(ghost.getPosition(), playerPosition) < LAZY_DISTINCTION) {
            return targetCorner;
        } else {
            return playerPosition;
        }
    }
}