package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.WikiJump;

public abstract class RectInteractable extends Interactable {

    protected Rectangle rect;
    protected Fixture fixture;
    protected Body body;
    protected float height;
    protected float width;

    public RectInteractable(World world, TiledMap map, Rectangle rect) {

        super(world, map);
        this.rect = rect;

        height = rect.getHeight();
        width = rect.getWidth();


        /** Creating a body */

        //prepare for body creation
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        // configure a bodyDef
        bodyDef.type = BodyDef.BodyType.StaticBody;
        float x = (rect.getX() + rect.getWidth() / 2) / WikiJump.PPM;
        float y = (rect.getY() + rect.getHeight() / 2) / WikiJump.PPM;
        bodyDef.position.set(x, y);

        // apply bodyDef to create a body in our world, leave a reference for the body
        body = world.createBody(bodyDef);

        // configure and create a fixture for the body
        polygonShape.setAsBox((rect.getWidth() / 2)/WikiJump.PPM, (rect.getHeight() / 2)/WikiJump.PPM);
        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef);
    }
}
