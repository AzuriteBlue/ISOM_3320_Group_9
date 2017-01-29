package sprite;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class Ladder extends RectInteractable {
    public Ladder(World world, TiledMap map, Rectangle rect) {
        super(world, map, rect);
        fixture.setUserData(this);
    }

    @Override
    public void onHit(Wiki wiki) {
        System.out.println("ladder");
        wiki.body.setLinearVelocity(0, 8);

    }
}
