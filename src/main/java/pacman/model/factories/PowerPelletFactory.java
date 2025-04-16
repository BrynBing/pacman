package pacman.model.factories;

import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.staticentity.collectable.Pellet;

/**
 * Concrete renderable factory for Power Pellet objects, inheriting from PelletFactory
 */
public class PowerPelletFactory extends PelletFactory {
//    private static final Image PELLET_IMAGE = new Image("maze/pellet.png");
    private static final int POWER_PELLET_POINTS = 50;
    private static final double SIZE_MULTIPLIER = 2.0; // Power pellet is twice the size
    private static final Vector2D POSITION_ADJUSTMENT = new Vector2D(-8, -8);

    @Override
    public Renderable createRenderable(
            Vector2D position
    ) {
        try {
            // Adjust the bounding box size to be twice that of the normal pellet
            BoundingBox boundingBox = new BoundingBoxImpl(
                    position.add(POSITION_ADJUSTMENT),
                    PELLET_IMAGE.getHeight() * SIZE_MULTIPLIER,
                    PELLET_IMAGE.getWidth() * SIZE_MULTIPLIER
            );

            return new Pellet(
                    boundingBox,
                    Renderable.Layer.BACKGROUND,
                    PELLET_IMAGE,
                    POWER_PELLET_POINTS
            );

        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid power pellet configuration | %s", e));
        }
    }
}
