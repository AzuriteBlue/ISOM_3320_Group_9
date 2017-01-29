package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Interactable {

    protected World world;
    protected TiledMap map;


    public Interactable(World world, TiledMap map) {
        this.world = world;
        this.map = map;
    }

    public abstract void onHit(Wiki wiki);

}
