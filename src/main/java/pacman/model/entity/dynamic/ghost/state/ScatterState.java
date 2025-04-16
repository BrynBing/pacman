package pacman.model.entity.dynamic.ghost.state;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScatterState implements GhostState {
    private int currentDirectionCount = 0;
    private static final int minimumDirectionCount = 8;

    @Override
    public void updateImage(GhostImpl ghost) {
        ghost.setImage(ghost.getOrigonalImage());
    }

    @Override
    public Direction selectDirection(GhostImpl ghost, Set<Direction> possibleDirections) {
        Direction currentDirection = ghost.getDirection();

        if (possibleDirections.isEmpty()) {
            return currentDirection;
        }

        // ghosts have to continue in a direction for a minimum time before changing direction
        if (currentDirection != null && currentDirectionCount < minimumDirectionCount) {
            currentDirectionCount++;
            return currentDirection;
        }

        Map<Direction, Double> distances = new HashMap<>();

        for (Direction direction : possibleDirections) {
            // ghosts never choose to reverse travel
            if (currentDirection == null || direction != currentDirection.opposite()) {
                distances.put(direction, Vector2D.calculateEuclideanDistance(ghost.getKinematicState().getPotentialPosition(direction), ghost.getTargetLocation()));
            }
        }

        // only go the opposite way if trapped
        if (distances.isEmpty()) {
            return currentDirection.opposite();
        }

        // select the direction that will reach the target location fastest
        Direction newDirection = Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey();

        if (!newDirection.equals(currentDirection)) {
            this.currentDirectionCount = 0;
        }

        return newDirection;
    }
}
