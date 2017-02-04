package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.Util.BodyCreator;

public abstract class RectInteractable extends Interactable {

    protected Rectangle rect;
    protected Body body;
    protected float height;
    protected float width;

    public RectInteractable(World world, TiledMap map, Rectangle rect) {

        super(world, map);
        this.rect = rect;
        this.height = rect.getHeight();
        this.width = rect.getWidth();
        body = BodyCreator.createRectangleBody(world, rect);
    }
}
