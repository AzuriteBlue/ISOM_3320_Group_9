package sprite;



import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.Screens.GameOverScreen;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;


public class Wiki extends Sprite{

    public World world;
    public Body body;
    public PlayScreen playScreen;
    private static int side = 9;

    public Direction direction;
    public boolean jumped = false;
    private boolean shootable = true;
    private float shootTimeCount = 0;
    private float shootInterval = 0.2f;
    private float jumpV = 6;

    private static Texture wikiTexture0 = new Texture("Sprite/Wiki/Wiki0.png");
    private static Texture wikiTexture1 = new Texture("Sprite/Wiki/Wiki1.png");
    private static Texture wikiTexture2 = new Texture("Sprite/Wiki/Wiki2.png");
//    private static Texture boost1Texture = new Texture("Sprite/Wiki_boost1.png");
    private static Texture boost2Texture = new Texture("Sprite/Wiki_boost2.png");


    private Animation runAnimation;
    private float stateTime;



    public Wiki(World world, PlayScreen playScreen) {

        super(wikiTexture0);

        setBounds(0,0, side*3/WikiJump.PPM, side*3/WikiJump.PPM);

        this.world = world;
        this.playScreen = playScreen;

        // spawn wiki
        body = BodyCreator.createRectangleBody(world, 32, 150, side, side); // 32 150
//        body.getFixtureList().get(0).setFriction(5f);
        body.getFixtureList().get(0).setUserData(this);
        direction = Direction.RIGHT;

        // create running animation
        Array<Texture> frames = new Array<Texture>();
        frames.add(wikiTexture0);
        frames.add(wikiTexture1);
        frames.add(wikiTexture2);
        runAnimation = new Animation(0.1f, frames);

        // play spawn sound
        new Thread(new Runnable() {
            @Override
            public void run() {
                Sound sound = WikiJump.assetManager.get("audio/sound/wiki-spawn.mp3", Sound.class);
                sound.play();
            }
        }).run();

    }


    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);

        // change texture according to current state
        // (jumping or running)
        if (body.getLinearVelocity().y > 0) {
            if (!getTexture().equals(boost2Texture)) setTexture(boost2Texture);
        } else if (body.getLinearVelocity().x != 0 && body.getLinearVelocity().y == 0) {
            stateTime += delta;
            setTexture((Texture) runAnimation.getKeyFrame(stateTime,true));
        }

        // prevent wiki from shooting too often
        if (!shootable) {
            shootTimeCount += delta;
        } else {
            shootTimeCount = 0;
        }

        if (shootTimeCount >= shootInterval) {
            shootable = true;
        }



    }


    public void shoot() {
        if (shootable) {
            if (direction == Direction.RIGHT)
                playScreen.bullets.add(new Bullet(world, playScreen,
                        (int) ((body.getPosition().x) * WikiJump.PPM + side / 2 + 10),
                        (int) (body.getPosition().y * WikiJump.PPM),
                        direction));
            else
                playScreen.bullets.add(new Bullet(world, playScreen,
                        (int) ((body.getPosition().x) * WikiJump.PPM - side / 2 - 10),
                        (int) (body.getPosition().y * WikiJump.PPM),
                        direction));

        shootable = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Sound sound = WikiJump.assetManager.get("audio/sound/wiki-shoot.wav", Sound.class);
                sound.play(0.3f);
            }
        }).run();
        }
    }
    public void die() {

//        System.out.println("dead");

        // under god mode, nothing happens
        if (!WikiJump.godMode) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    HUD.addScore((int) (HUD.standardTime - HUD.getTime()));
                    HUD.addHighScore();
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Sound sound = WikiJump.assetManager.get("audio/sound/wiki-die.mp3", Sound.class);
                    sound.play();//0.3f);
                }
            });

            thread1.run();
            thread2.run();


            WikiJump game = playScreen.game;
            game.gameOverScreen = new GameOverScreen(game, false);
            game.setScreen(game.gameOverScreen);

        }

    }

    public void run(Direction direction) {

        // turn
        if (this.direction != direction) {
            this.flip(true,false);
            this.direction = direction;
        }

        // move body
        switch (direction) {

            case LEFT:{
                if (body.getLinearVelocity().x >= -2)
                    body.applyLinearImpulse(new Vector2(-0.3f, 0), body.getWorldCenter(), true);
//                    body.setLinearVelocity(-2f,body.getLinearVelocity().y);
                break;
            }

            case RIGHT:{
                if (body.getLinearVelocity().x <= 2)
                    body.applyLinearImpulse(new Vector2(0.3f, 0), body.getWorldCenter(), true);
//                    body.setLinearVelocity(2f,body.getLinearVelocity().y);
                break;
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

        // only gets called under god mode
        body.applyLinearImpulse(new Vector2(0, jumpV), body.getWorldCenter(), true);
    }
}
