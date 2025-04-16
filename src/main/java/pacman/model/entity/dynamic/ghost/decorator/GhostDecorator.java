package pacman.model.entity.dynamic.ghost.decorator;

import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.level.Level;

public abstract class GhostDecorator implements Ghost {
    Ghost ghost;

    public GhostDecorator(Ghost ghost) {
        this.ghost = ghost;
    }

    @Override
    public abstract void collideWith(Level level, Renderable renderable);
}
