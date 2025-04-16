package pacman.model.entity.dynamic.ghost.state;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.maze.Maze;

import java.util.*;

public class FrightenedState implements GhostState{
    private int currentDirectionCount = 0;
    private static final int minimumDirectionCount = 8;

    private final Image frightenedImage = new Image("maze/ghosts/frightened.png");

    @Override
    public void updateImage(GhostImpl ghost) {
        ghost.setImage(frightenedImage);
    }

    @Override
    public Direction selectDirection(GhostImpl ghost, Set<Direction> possibleDirections) {
        Direction currentDirection = ghost.getDirection();

        if (Maze.isAtIntersection(possibleDirections)) {
            if (possibleDirections.isEmpty()) {
                return currentDirection;
            }

            // ghosts have to continue in a direction for a minimum time before changing direction
            if (currentDirection != null && currentDirectionCount < minimumDirectionCount) {
                currentDirectionCount++;
                return currentDirection;
            }

            List<Direction> directionList = new ArrayList<>();
            for (Direction direction : possibleDirections) {
                // ghosts never choose to reverse travel
                if (currentDirection == null || direction != currentDirection.opposite()) {
                    directionList.add(direction);
                }
            }

            // only go the opposite way if trapped
            if (directionList.isEmpty()) {
                return currentDirection != null ? currentDirection.opposite() : currentDirection;
            }

            Collections.shuffle(directionList);
            Direction newDirection = directionList.get(0);

            if (!newDirection.equals(currentDirection)) {
                this.currentDirectionCount = 0;
            }
            return newDirection;
        }
        return currentDirection;
    }



}
