package sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Util.BodyCreator;
import com.isom.infrastructure.WikiJump;

public abstract class Enemy extends Sprite{

    protected World world;
    protected Body body;
    protected PlayScreen playScreen;

    public boolean isDead = false;

    public Enemy(World world, PlayScreen playScreen, int posX, int posY, Texture texture) {
        // posX and posY are in PIXELS!!
        super(texture);
        this.world = world;
        this.playScreen = playScreen;

    }

    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
    }

    public void die(){
        this.isDead = true;
        Sound sound = WikiJump.assetManager.get("audio/sound/enemy-die.mp3", Sound.class);
        sound.play();

    }

}
