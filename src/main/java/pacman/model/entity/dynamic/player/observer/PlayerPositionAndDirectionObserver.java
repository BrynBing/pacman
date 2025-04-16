package pacman.model.entity.dynamic.player.observer;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public interface PlayerPositionAndDirectionObserver {
    void update(Vector2D position, Direction direction);
}
