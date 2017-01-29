package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.WikiJump;

public abstract class CircleInteractable extends Interactable {

    protected Circle circle;
    protected Fixture fixture;
    protected Body body;

    public CircleInteractable(World world, TiledMap map, Ellipse ellipse) {

        super(world, map);
        // We already know all the ellipse we created in Tiled maps are actually circles
        float x = (ellipse.x + ellipse.height / 2) / WikiJump.PPM;
        float y = (ellipse.y + ellipse.height / 2) / WikiJump.PPM;
        float radius = (ellipse.height / 2) / WikiJump.PPM;
        circle = new Circle(x, y, radius);

        /** Creating a body */

        //prepare for body creation
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();

        // configure a bodyDef
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(circle.x, circle.y);

        // apply bodyDef to create a body in our world, leave a reference for the body
        body = world.createBody(bodyDef);

        // configure and create a fixture for the body
        circleShape.setRadius(circle.radius);
        fixtureDef.shape = circleShape;
        fixture = body.createFixture(fixtureDef);
    }
}
