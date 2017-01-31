package sprite;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;


public class Ladder extends RectInteractable {
    public Ladder(World world, TiledMap map, Rectangle rect) {
        super(world, map, rect);
        body.getFixtureList().get(0).setUserData(this);
    }

    @Override
    public void onWikiHit(Wiki wiki) {

        if (wiki.body.getLinearVelocity().y == 0) wiki.body.setLinearVelocity(0, 8);

    }
}
