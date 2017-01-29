package sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.WikiJump;

public class Wiki extends Sprite{
    public Body body; // box2D body
    public World world;
    public Fixture fixture;

    // TODO
    private static Texture wikiTexture = new Texture("Wiki.png");
    private TextureRegion textureRegion;

    private static int side = 8;





    public Wiki(World world) {

        // TODO
        super(wikiTexture);
        textureRegion = new TextureRegion(getTexture());
        setBounds(0,0, side*3/WikiJump.PPM, side*3/WikiJump.PPM);

        this.world = world;


        // create main character (not drawn on map)

//        BodyDef bodyDef = new BodyDef();
//        CircleShape circleShape = new CircleShape();
//        FixtureDef fixtureDef = new FixtureDef();
//
//        bodyDef.position.set(32 / WikiJump.PPM, 352 / WikiJump.PPM); // y=352
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        body = world.createBody(bodyDef);
//
//        circleShape.setRadius(8 / WikiJump.PPM);
//        fixtureDef.shape = circleShape;
//        body.createFixture(fixtureDef);

        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.position.set(32 / WikiJump.PPM, 352 / WikiJump.PPM); // y=352
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        polygonShape.setAsBox(side/WikiJump.PPM, side/WikiJump.PPM);
        fixtureDef.shape = polygonShape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);





    }



    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
    }
}
