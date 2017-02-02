package sprite;



import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.Screens.GameOverScreen;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;

public class Wiki extends Sprite{

    public World world;
    public Body body;
    public PlayScreen playScreen;
    private static int side = 8;

    public Direction direction;
//    public boolean boosted = false;
    public boolean jumped = false;
    private boolean shootable = true;
    private float shootTimeCount = 0;
    private float shootInterval = 0.2f;
    private float jumpV = 6;

    Sound sound = WikiJump.assetManager.get("audio/sound/wiki-shoot.wav", Sound.class);



    // TODO
    private static Texture wikiTexture = new Texture("Sprite/Wiki.png");
//    private static Texture boost1Texture = new Texture("Sprite/Wiki_boost1.png");
    private static Texture boost2Texture = new Texture("Sprite/Wiki_boost2.png");




    public Wiki(World world, PlayScreen playScreen) {

        // TODO
        super(wikiTexture);
        setBounds(0,0, side*3/WikiJump.PPM, side*3/WikiJump.PPM);


        this.world = world;
        this.playScreen = playScreen;
        body = BodyCreator.createRectangleBody(world, 32, 150, side, side);
//        body.getFixtureList().get(0).setFriction(5f);
        body.getFixtureList().get(0).setUserData(this);

        direction = Direction.RIGHT;

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

//        // change texture when boosting
//        if (body.getLinearVelocity().y > jumpV) {
//            setTexture(boost2Texture);
//        } else if (body.getLinearVelocity().y > 0) {
//            setTexture(boost1Texture);
//        } else {
//            if (!getTexture().equals(wikiTexture)) setTexture(wikiTexture);
//        }

        if (body.getLinearVelocity().y > 0.5) {
            if (!getTexture().equals(boost2Texture)) setTexture(boost2Texture);
        } else {
            if (!getTexture().equals(wikiTexture)) setTexture(wikiTexture);
        }

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
        if (this.direction != direction) {
            this.flip(true,false);
            this.direction = direction;
        }
        switch (direction) {

            case LEFT:{
                if (body.getLinearVelocity().x >= -2)
                    body.applyLinearImpulse(new Vector2(-0.4f, 0), body.getWorldCenter(), true);
//                    body.setLinearVelocity(-2f,body.getLinearVelocity().y);

            }

            case RIGHT:{
                if (body.getLinearVelocity().x <= 2)
                    body.applyLinearImpulse(new Vector2(0.3f, 0), body.getWorldCenter(), true);
//                    body.setLinearVelocity(2f,body.getLinearVelocity().y);
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

        body.applyLinearImpulse(new Vector2(0, jumpV), body.getWorldCenter(), true);

    }
}
