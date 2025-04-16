package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyChaseStrategy implements ChaseStrategy {
    private GhostImpl blinky;

    public InkyChaseStrategy(GhostImpl blinky) {
        this.blinky = blinky;
    }

    @Override
    public Vector2D chase(Ghost ghost, Vector2D playerPosition, Direction playerDirection) {
        if (blinky != null) {
            Vector2D blinkyPosition = blinky.getPosition();
            Vector2D pacmanFuturePosition = playerPosition.add(playerDirection.toVector().multiply(2));
            Vector2D vector = pacmanFuturePosition.subtract(blinkyPosition).multiply(2);
            Vector2D targetPosition = blinkyPosition.add(vector);
            return targetPosition;
        } else {
            System.out.println("Blinky information is missing.");
            return null;
        }
    }
}

