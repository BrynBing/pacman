package pacman.model.entity.dynamic.ghost.decorator;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.level.Level;

public class FrightenedGhost extends GhostDecorator {
    private boolean isEaten;

    public FrightenedGhost(Ghost ghost) {
        super(ghost);
    }

    // Decorate this method
    @Override
    public void collideWith(Level level, Renderable renderable) {
        if (level.isPlayer(renderable)) {
//            System.out.println("ghost eaten");
            handleEaten();
        }
    }

    private void handleEaten() {
        if (!isEaten) {
            isEaten = true;
            // Reset ghost position
            ghost.reset();
//            System.out.println("ghost reset");

            // Schedule a timer to switch ghost back to SCATTER mode after delay
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.schedule(() -> {
                ghost.setGhostMode(GhostMode.SCATTER);
//                System.out.println("delayed");
            }, 1, TimeUnit.SECONDS);
            ses.shutdown();
        }
    }

    @Override
    public void update() {
        ghost.update();
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return ghost.getPositionBeforeLastUpdate();
    }

    @Override
    public void setPosition(Vector2D position) {
        ghost.setPosition(position);
    }

    @Override
    public boolean collidesWith(Renderable renderable) {
        return ghost.collidesWith(renderable);
    }

    @Override
    public Image getImage() {
        return ghost.getImage();
    }

    @Override
    public void setPossibleDirections(Set<Direction> possibleDirections) {
        ghost.setPossibleDirections(possibleDirections);
    }

    @Override
    public Direction getDirection() {
        return ghost.getDirection();
    }

    @Override
    public Vector2D getCenter() {
        return ghost.getCenter();
    }

    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        ghost.setSpeeds(speeds);
    }

    @Override
    public void setGhostMode(GhostMode ghostMode) {
        ghost.setGhostMode(ghostMode);
    }

    @Override
    public double getWidth() {
        return ghost.getWidth();
    }

    @Override
    public double getHeight() {
        return ghost.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return ghost.getPosition();
    }

    @Override
    public Layer getLayer() {
        return ghost.getLayer();
    }

    @Override
    public BoundingBox getBoundingBox() {
        return ghost.getBoundingBox();
    }

    @Override
    public void reset() {
        ghost.reset();
    }

    @Override
    public void update(Vector2D position, Direction direction) {
        ghost.update(position, direction);
    }
}

