package sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;




public class Sentry extends Enemy {

    private static int side = 18;

    // TODO
    private static Texture sentryTexture = new Texture("Sprite/Sentry.png");


    private double timeCount = 0;
    private Direction direction;


    public Sentry(World world, PlayScreen playScreen, int posX, int posY, Direction direction) {

        // TODO
        super(world, playScreen, posX, posY, sentryTexture);
        this.direction = direction;


        setBounds(0,0, side*2.3f/ WikiJump.PPM, side*2.3f/WikiJump.PPM);

        body = BodyCreator.createRectangleBody(world, posX, posY, side, side);
        body.getFixtureList().get(0).setUserData(this);
    }



    public void update(float delta) {
        super.update(delta);

        // shoot
        if (HUD.getTime() > timeCount) {
            shoot();
            timeCount = HUD.getTime() + 1;
        }


    }


    private void shoot() {

        //System.out.println("shoot");

        if (direction == Direction.LEFT)
            playScreen.bullets.add(
                    new Bullet(world,
                            playScreen,
                            (int)((body.getPosition().x) * WikiJump.PPM - side/2 - 17),
                            (int)(body.getPosition().y * WikiJump.PPM + 10), direction));
        if (direction == Direction.RIGHT)
            playScreen.bullets.add(
                    new Bullet(world,
                            playScreen,
                            (int)((body.getPosition().x) * WikiJump.PPM + side/2 + 17),
                            (int)(body.getPosition().y * WikiJump.PPM + 10), direction));
    }

    @Override
    public void die() {
        super.die();
        if (playScreen.sentries.contains(this, true)) {
            int index = playScreen.sentries.indexOf(this, true);
            playScreen.sentries.removeIndex(index);
        }
        HUD.addScore(70);
    }
}

