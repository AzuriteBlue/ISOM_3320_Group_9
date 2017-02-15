package com.isom.infrastructure.Util;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.WikiJump;



public class BodyCreator {

    private static World world;

    private static BodyDef bodyDef;
    private static PolygonShape polygonShape;
    private static CircleShape circleShape;
    private static FixtureDef fixtureDef;
    private static Body body;
    private static Fixture fixture;

    public static Body createRectangleBody(World world, int posX, int posY, int sideX, int sideY) {
        bodyDef = new BodyDef();
        polygonShape = new PolygonShape();
        fixtureDef = new FixtureDef();

        bodyDef.position.set(posX/WikiJump.PPM, posY/WikiJump.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        polygonShape.setAsBox(sideX/WikiJump.PPM, sideY/WikiJump.PPM);
        fixtureDef.shape = polygonShape;

        fixture.setFriction(0.3f);

        fixture = body.createFixture(fixtureDef);



        return body;
    }

    public static Body createRectangleBody(World world, Rectangle rect) {

        //prepare for body creation
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        polygonShape = new PolygonShape();


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

        return body;
    }

    public static Body createCircleBody(World world, Ellipse ellipse) {

        // prepare for body creation
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        circleShape = new CircleShape();

        // construct a circle according to the ellipse
        float x = (ellipse.x + ellipse.height / 2) / WikiJump.PPM;
        float y = (ellipse.y + ellipse.height / 2) / WikiJump.PPM;
        float radius = (ellipse.height / 2) / WikiJump.PPM;
        Circle circle = new Circle(x, y, radius);


        // configure a bodyDef
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(circle.x, circle.y);

        // apply bodyDef to create a body in our world, leave a reference for the body
        body = world.createBody(bodyDef);

        // configure and create a fixture for the body
        circleShape.setRadius(circle.radius);
        fixtureDef.shape = circleShape;
        fixture = body.createFixture(fixtureDef);

        return body;
    }
}
