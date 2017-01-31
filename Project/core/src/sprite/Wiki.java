package sprite;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;

public class Wiki extends Sprite{

    public World world;
    public Body body;
    public PlayScreen playScreen;
    private static int side = 8;

    public Direction direction;
    public boolean boosted = false;
    public boolean jumped = false;
    public float jumpV = 4;


    // TODO
    private static Texture wikiTexture = new Texture("Sprite/Wiki.png");
    private static Texture boost1Texture = new Texture("Sprite/Wiki_boost1.png");
    private static Texture boost2Texture = new Texture("Sprite/Wiki_boost2.png");
    //private TextureRegion textureRegion;



    public Wiki(World world, PlayScreen playScreen) {

        // TODO
        super(wikiTexture);
        //textureRegion = new TextureRegion(getTexture());
        setBounds(0,0, side*3/WikiJump.PPM, side*3/WikiJump.PPM);


        this.world = world;
        this.playScreen = playScreen;
        body = BodyCreator.createRectangleBody(world, 32, 352, side, side);
        body.getFixtureList().get(0).setUserData(this);

        direction = Direction.RIGHT;
    }



    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);

        // change texture when boosting
        if (body.getLinearVelocity().y > jumpV) {
            setTexture(boost2Texture);
        } else if (body.getLinearVelocity().y > 0) {
            setTexture(boost1Texture);
        } else {
            if (!getTexture().equals(wikiTexture)) setTexture(wikiTexture);
        }
    }


    public void shoot() {
        if (direction == Direction.RIGHT)
            playScreen.bullets.add(new Bullet(world, playScreen,
                    (int)((body.getPosition().x) * WikiJump.PPM + side/2 + 10),
                    (int)(body.getPosition().y * WikiJump.PPM),
                    direction));
        else
            playScreen.bullets.add(new Bullet(world, playScreen,
                    (int)((body.getPosition().x) * WikiJump.PPM - side/2 - 10),
                    (int)(body.getPosition().y * WikiJump.PPM),
                    direction));
    }
    public void die() {
        playScreen.game.create();
    }

    public void run(Direction direction) {
        if (this.direction != direction) {
            this.flip(true,false);
            this.direction = direction;
        }
        switch (direction) {

            case LEFT:{
                if (body.getLinearVelocity().x >= -2)
                    body.applyLinearImpulse(new Vector2(-0.2f, 0), body.getWorldCenter(), true);

            }

            case RIGHT:{
                if (body.getLinearVelocity().x <= 2)
                    body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
            }
        }
        //System.out.println(body.getLinearVelocity());
    }

    public void jump() {
        if (body.getLinearVelocity().y == 0) {
            body.applyLinearImpulse(new Vector2(0, jumpV), body.getWorldCenter(), true);
            jumped = true;
        }
    }

    public void boost() {
        if (body.getLinearVelocity().y <= 6f) {
            body.applyLinearImpulse(new Vector2(0, jumpV), body.getWorldCenter(), true);
            boosted = true;
        }

    }
}
