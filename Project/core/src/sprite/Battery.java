package sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.WikiJump;

public class Battery extends RectInteractable {

    private boolean collected = false;

    public Battery(World world, TiledMap map, Rectangle rect) {
        super(world, map, rect);
        body.getFixtureList().get(0).setUserData(this);
    }

    @Override
    public void onWikiHit(Wiki wiki) {}


    public void collectBy(Wiki wiki) {

        // 1. add score
        if (!this.collected) {
            HUD.addScore(100);

            // 2. play sound
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Sound sound = WikiJump.assetManager.get("audio/sound/picked.mp3", Sound.class);
                    sound.play(0.3f);
                }
            }).run();

            // 3. turn off collision for that body
            collected = true;

            // 4. turn off rendering of this object
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(2);
            int lowerY = (int) (body.getPosition().y * WikiJump.PPM / 16);
            int upperY = lowerY + 1;
            int leftX = (int) (body.getPosition().x * WikiJump.PPM / 16);
            int rightX = leftX + 1;

            tileLayer.getCell(leftX, lowerY).setTile(null);
            tileLayer.getCell(leftX, upperY).setTile(null);
            tileLayer.getCell(rightX, lowerY).setTile(null);
            tileLayer.getCell(rightX, upperY).setTile(null);
        }

    }
}
