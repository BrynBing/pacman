package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class PinkyChaseStrategy implements ChaseStrategy {
    @Override
    public Vector2D chase(Ghost ghost, Vector2D playerPosition, Direction playerDirection) {
        Vector2D predictedPosition = playerPosition.add(playerDirection.toVector().multiply(4));
        return predictedPosition;
    }
}
