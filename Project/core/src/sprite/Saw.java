package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.World;

public class Saw extends CircleInteractable {

    public Saw(World world, TiledMap map, Ellipse ellipse) {
        super(world, map, ellipse);
        fixture.setUserData(this);
    }

    @Override
    public void onHit(Wiki wiki) {
        System.out.println("saw");
    }
}
