package sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;

public class Bullet extends Sprite {

    public World world;
    public Body body;
    public PlayScreen playScreen;
    public boolean destroyed = false;

    private static int sideX = 3;
    private static int sideY = 1;

    public Direction direction;



    // TODO
    private static Texture bulletTexture = new Texture("Sprite/Bullet.png");



    public Bullet(World world, PlayScreen playScreen, int posX, int posY, Direction direction) {

        // TODO
        super(bulletTexture);
        setBounds(0,0, sideX*3/ WikiJump.PPM, sideY*3/WikiJump.PPM);


        this.world = world;
        this.playScreen = playScreen;
        body = BodyCreator.createRectangleBody(world, posX, posY, sideX, sideY);
        body.getFixtureList().get(0).setUserData(this);

        // no gravity for bullets
        body.setGravityScale(0);

        this.direction = direction;
        if (direction == Direction.RIGHT) {
            body.setLinearVelocity(4f, 0);
        } else {
            body.setLinearVelocity(-4f, 0);
        }
    }



    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);

    }

    public void destroy() {
        this.destroyed = true;

        // remove reference
        if (playScreen.bullets.contains(this, true)) {
            int index = playScreen.bullets.indexOf(this, true);
            playScreen.bullets.removeIndex(index);
        }
    }

}
