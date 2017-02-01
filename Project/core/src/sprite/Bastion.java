package sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;



public class Bastion extends Enemy {

    private static int side = 18;

    // TODO
    private static Texture bastionTexture = new Texture("Sprite/Bastion.png");
    //private TextureRegion textureRegion;

    private int leftLimit;
    private int rightLimit;
    private int currentX;

    public Direction direction;
    private double timeCount = 0;


    public Bastion(World world, PlayScreen playScreen, int posX, int posY, int leftLimit, int rightLimit, Direction direction) {

        // TODO
        super(world, playScreen, posX, posY, bastionTexture);

        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        this.direction = direction;


        //textureRegion = new TextureRegion(getTexture());
        setBounds(0,0, side*2.3f/ WikiJump.PPM, side*2.3f/WikiJump.PPM);

        body = BodyCreator.createRectangleBody(world, posX, posY, side, side);
        body.getFixtureList().get(0).setUserData(this);


    }



    public void update(float delta) {
        super.update(delta);
        switch (direction) {
            case LEFT: {
                body.setLinearVelocity(-0.5f, 0);
                break;
            }
            case RIGHT: {
                body.setLinearVelocity(0.5f, 0);
                break;
            }
        }
        currentX = (int)(getX()*WikiJump.PPM);

        if (currentX < leftLimit) {
            direction = Direction.RIGHT;
            this.flip(true,false);
        } else if (currentX > rightLimit) {
            direction = Direction.LEFT;
            this.flip(true,false);
        }


        // shoot
        if (playScreen.hud.getTime() > timeCount) {
            shoot();
            timeCount = playScreen.hud.getTime() + 1;
        }


    }


    public void shoot() {

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
        if (playScreen.bastions.contains(this, true)) {
            int index = playScreen.bastions.indexOf(this, true);
            playScreen.bastions.removeIndex(index);
        }
        HUD.addScore(70);
    }
}
