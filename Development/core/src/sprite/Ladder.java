package sprite;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class Ladder extends RectInteractable {
    public Ladder(World world, TiledMap map, Rectangle rect) {
        super(world, map, rect);
        body.getFixtureList().get(0).setUserData(this);
        body.getFixtureList().get(0).setFriction(0);
    }

    @Override
    public void onWikiHit(Wiki wiki) {

        // if wiki is not falling straight down
        // to prevent multiple continuous boost from one ladder
        if (wiki.body.getLinearVelocity().y >= 0 || wiki.body.getLinearVelocity().x != 0)
            wiki.body.applyLinearImpulse(new Vector2(0, 8f - wiki.body.getLinearVelocity().y), wiki.body.getWorldCenter(), true);

    }
}
