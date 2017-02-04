package sprite;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;




public class Sentry extends Enemy {

    private static float shootInterval = 0.15f;
    private static float sleepTime = 2;
    private double lastShootTime;
    private double nextShootTime;
    private int i = 0;

    private boolean isFixed = false;


    private static int side = 18;

    // TODO
    private static Texture sentryTexture = new Texture("Sprite/sentry.png");


    private double timeCount = 0;
    private Direction direction;


    public Sentry(World world, PlayScreen playScreen, int posX, int posY, Direction direction) {



        // TODO
        super(world, playScreen, posX, posY, sentryTexture);
        this.direction = direction;
        if (direction.equals(Direction.RIGHT)) this.flip(true, false);


        setBounds(0,0, side*2.3f/ WikiJump.PPM, side*2.3f/WikiJump.PPM);

        body = BodyCreator.createRectangleBody(world, posX, posY, side, side);
        body.getFixtureList().get(0).setUserData(this);


    }



    public void update(float delta) {
        super.update(delta);

        // shoot
        // shoot 6 continuous bullets, wait for some time, shoot 6 again.
        if (i<5) {
            if (timeCount != 0) timeCount = 0;
            if (HUD.getTime() > nextShootTime) {
                shoot();
                lastShootTime = HUD.getTime();
                nextShootTime = lastShootTime + shootInterval;
                i++;
            }
        } else {
            timeCount += delta;
            if (timeCount > sleepTime) i = 0;
        }


        // spawn higher than ground level, when it touches the ground, set it to be static.
        if (!isFixed && body.getLinearVelocity().y == 0) {
            body.setType(BodyDef.BodyType.StaticBody);
            isFixed = true;
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

