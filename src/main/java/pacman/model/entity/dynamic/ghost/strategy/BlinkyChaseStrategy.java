package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class BlinkyChaseStrategy implements ChaseStrategy {
    @Override
    public Vector2D chase(Ghost ghost, Vector2D playerPosition, Direction playerDirection) {
        return playerPosition;
    }
}
