package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;


/**
 * Concrete renderable factory for Ghost objects
 */
public class GhostFactory implements RenderableFactory {

    private static final int RIGHT_X_POSITION_OF_MAP = 448;
    private static final int TOP_Y_POSITION_OF_MAP = 16 * 3;
    private static final int BOTTOM_Y_POSITION_OF_MAP = 16 * 34;

    private static final Image BLINKY_IMAGE = new Image("maze/ghosts/blinky.png");
    private static final Image INKY_IMAGE = new Image("maze/ghosts/inky.png");
    private static final Image CLYDE_IMAGE = new Image("maze/ghosts/clyde.png");
    private static final Image PINKY_IMAGE = new Image("maze/ghosts/pinky.png");
    private final Image GHOST_IMAGE;
    Vector2D targetCorner;

    public GhostFactory(char type) {
        switch (type) {
            case RenderableType.BLINKY:
                this.GHOST_IMAGE = BLINKY_IMAGE;
                this.targetCorner = new Vector2D(RIGHT_X_POSITION_OF_MAP, TOP_Y_POSITION_OF_MAP);
//                this.chaseStrategy = new BlinkyChaseStrategy();
                break;
            case RenderableType.PINKY:
                this.GHOST_IMAGE = PINKY_IMAGE;
                this.targetCorner = new Vector2D(0, TOP_Y_POSITION_OF_MAP);
//                this.chaseStrategy = new PinkyChaseStrategy();
                break;
            case RenderableType.INKY:
                this.GHOST_IMAGE = INKY_IMAGE;
                this.targetCorner = new Vector2D(RIGHT_X_POSITION_OF_MAP, BOTTOM_Y_POSITION_OF_MAP);
//                this.chaseStrategy = new InkyChaseStrategy(blinky);
                break;
            case RenderableType.CLYDE:
                this.GHOST_IMAGE = CLYDE_IMAGE;
                this.targetCorner = new Vector2D(0, BOTTOM_Y_POSITION_OF_MAP);
//                this.chaseStrategy = new ClydeChaseStrategy();
                break;
            default:
                throw new IllegalArgumentException("Invalid ghost type");
        }
    }

    @Override
    public Renderable createRenderable(
            Vector2D position
    ) {
        try {
            position = position.add(new Vector2D(4, -4));

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    GHOST_IMAGE.getHeight(),
                    GHOST_IMAGE.getWidth()
            );

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .build();

            GhostImpl ghost = new GhostImpl(
                    GHOST_IMAGE,
                    boundingBox,
                    kinematicState,
                    GhostMode.SCATTER,
                    targetCorner);

            return ghost;
        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid ghost configuration | %s ", e));
        }
    }
}
