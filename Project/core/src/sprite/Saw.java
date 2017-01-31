package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.World;

public class Saw extends CircleInteractable {

    public Saw(World world, TiledMap map, Ellipse ellipse) {
        super(world, map, ellipse);
        body.getFixtureList().get(0).setUserData(this);
    }

    @Override
    public void onWikiHit(Wiki wiki) {
        wiki.die();
    }
}
