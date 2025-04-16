package pacman.model.entity.dynamic.ghost.state;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;

import java.util.Set;

public interface GhostState {
    void updateImage(GhostImpl ghost);
    Direction selectDirection(GhostImpl ghost, Set<Direction> possibleDirections);
}
