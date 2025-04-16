package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.state.*;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.physics.*;
import pacman.model.level.Level;
import pacman.model.maze.Maze;

import java.util.*;

/**
 * Concrete implementation of Ghost entity in Pac-Man Game
 */
public class GhostImpl implements Ghost {

//    private static final int minimumDirectionCount = 8;
    private final Layer layer = Layer.FOREGROUND;
    private final Image originalImage;
    private final BoundingBox boundingBox;
    private final Vector2D startingPosition;
    private final Vector2D targetCorner;
    private KinematicState kinematicState;
    private GhostMode ghostMode;
//    private Vector2D targetLocation;
    private Vector2D playerPosition;
    private Direction playerDirection;
    private Direction currentDirection;
    private Set<Direction> possibleDirections;
    private Map<GhostMode, Double> speeds;
//    private int currentDirectionCount = 0;
    private ChaseStrategy chaseStrategy;
    private GhostState ghostState;
    private Map<GhostMode, GhostState> stateMap;
    private Image currentImage;

    public GhostImpl(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, Vector2D targetCorner) {
        this.originalImage = image;
        this.currentImage = image;
        this.boundingBox = boundingBox;
        this.kinematicState = kinematicState;
        this.startingPosition = kinematicState.getPosition();
        this.ghostMode = ghostMode;
        this.possibleDirections = new HashSet<>();
        this.targetCorner = targetCorner;
        //this.targetLocation = getTargetLocation();
        this.currentDirection = null;

        this.stateMap = new EnumMap<>(GhostMode.class);
        this.stateMap.put(GhostMode.CHASE, new ChaseState());
        this.stateMap.put(GhostMode.SCATTER, new ScatterState());
        this.stateMap.put(GhostMode.FRIGHTENED, new FrightenedState());

        this.ghostState = stateMap.get(this.ghostMode);
    }

    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        this.speeds = speeds;
    }

    @Override
    public Image getImage() {
        return currentImage;
    }

    public Image getOrigonalImage() {
        return originalImage;
    }

    public void setImage(Image image) {
        this.currentImage = image;
    }

    @Override
    public void update() {
        this.updateDirection();
        this.kinematicState.update();
        this.boundingBox.setTopLeft(this.kinematicState.getPosition());
    }

    private void updateDirection() {
        // Ghosts update their target location when they reach an intersection
        /*if ((ghostMode == GhostMode.SCATTER || ghostMode == GhostMode.CHASE) && Maze.isAtIntersection(this.possibleDirections)) {
            this.targetLocation = getTargetLocation();
        }*/

        Direction newDirection = this.ghostState.selectDirection(this, this.possibleDirections);

        // Ghosts have to continue in a direction for a minimum time before changing direction
        /*if (this.currentDirection != newDirection) {
            this.currentDirectionCount = 0;
        }*/
        this.currentDirection = newDirection;

        switch (currentDirection) {
            case LEFT -> this.kinematicState.left();
            case RIGHT -> this.kinematicState.right();
            case UP -> this.kinematicState.up();
            case DOWN -> this.kinematicState.down();
        }
    }

    public Vector2D getTargetLocation() {
        if (ghostState instanceof ChaseState) {
            return this.chaseStrategy.chase(this, playerPosition, playerDirection);
        } else if (ghostState instanceof ScatterState) {
            return this.targetCorner;
        }
        throw new IllegalStateException("Unexpected state: " + ghostState);
    }

    /*public Direction selectDirection(Set<Direction> possibleDirections) {
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
                distances.put(direction, Vector2D.calculateEuclideanDistance(this.kinematicState.getPotentialPosition(direction), this.targetLocation));
            }
        }

        // only go the opposite way if trapped
        if (distances.isEmpty()) {
            return currentDirection.opposite();
        }

        // select the direction that will reach the target location fastest
        return Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
*/
    @Override
    public void setGhostMode(GhostMode ghostMode) {
        this.ghostMode = ghostMode;
        // update ghost state
        this.ghostState = stateMap.get(ghostMode);
        ghostState.updateImage(this);
        this.kinematicState.setSpeed(speeds.get(ghostMode));
        // ensure direction is switched
//        this.currentDirectionCount = minimumDirectionCount;
    }

    @Override
    public boolean collidesWith(Renderable renderable) {
        return boundingBox.collidesWith(kinematicState.getSpeed(), kinematicState.getDirection(), renderable.getBoundingBox());
    }

    @Override
    public void collideWith(Level level, Renderable renderable) {
        if (level.isPlayer(renderable)) {
            level.handleLoseLife();
        }
    }

    @Override
    public void update(Vector2D playerPosition, Direction playerDirection) {
        this.playerPosition = playerPosition;
        this.playerDirection = playerDirection;
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return this.kinematicState.getPreviousPosition();
    }

    @Override
    public double getHeight() {
        return this.boundingBox.getHeight();
    }

    @Override
    public double getWidth() {
        return this.boundingBox.getWidth();
    }

    @Override
    public Vector2D getPosition() {
        return this.kinematicState.getPosition();
    }

    @Override
    public void setPosition(Vector2D position) {
        this.kinematicState.setPosition(position);
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void reset() {
        // return ghost to starting position
        this.kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(startingPosition)
                .build();
        this.boundingBox.setTopLeft(startingPosition);
        this.ghostMode = GhostMode.SCATTER;
        this.ghostState = stateMap.get(ghostMode);
        ghostState.updateImage(this);
//        this.currentDirectionCount = minimumDirectionCount;
    }

    @Override
    public void setPossibleDirections(Set<Direction> possibleDirections) {
        this.possibleDirections = possibleDirections;
    }

    @Override
    public Direction getDirection() {
        return this.kinematicState.getDirection();
    }

    @Override
    public Vector2D getCenter() {
        return new Vector2D(boundingBox.getMiddleX(), boundingBox.getMiddleY());
    }

    public void setChaseStrategy(ChaseStrategy chaseStrategy) {
        this.chaseStrategy = chaseStrategy;
    }

    public KinematicState getKinematicState() {
        return kinematicState;
    }

    public GhostState getGhostState() {
        return ghostState;
    }

    public void setGhostState(GhostState ghostState) {
        this.ghostState = ghostState;
    }
}
