package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.Util.BodyCreator;

public abstract class CircleInteractable extends Interactable {

    protected Body body;

    public CircleInteractable(World world, TiledMap map, Ellipse ellipse) {

        super(world, map);
        body = BodyCreator.createCircleBody(world, ellipse);
    }
}
