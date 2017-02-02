package sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.Screens.GameOverScreen;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.WikiJump;

public class Gate extends RectInteractable {

    PlayScreen playScreen;

    public Gate(World world, TiledMap map, PlayScreen playScreen, Rectangle rect) {
        super(world, map, rect);
        body.getFixtureList().get(0).setUserData(this);
        this.playScreen = playScreen;
    }

    @Override
    public void onWikiHit(Wiki wiki) {


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                HUD.addScore((int) (HUD.standardTime - HUD.getTime()));
                HUD.addScore(3000);
                HUD.addHighScore();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Sound sound = WikiJump.assetManager.get("audio/sound/win.mp3", Sound.class);
                sound.play();//0.3f);
            }
        });


        thread1.run();
        thread2.run();


        WikiJump game = playScreen.game;
        game.gameOverScreen = new GameOverScreen(game, true);
        game.setScreen(game.gameOverScreen);
    }
}
