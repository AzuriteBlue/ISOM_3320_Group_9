package sprite;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.WikiJump;

public class Battery extends RectInteractable {

    public Battery(World world, TiledMap map, Rectangle rect) {
        super(world, map, rect);
        fixture.setUserData(this);
    }

    @Override
    public void onHit(Wiki wiki) {
        //System.out.println("battery");

        // 1. add score
        HUD.addScore(100);

        // 2. turn off collision for that body
        world.step(0,0,0);
        body.setActive(false);
        world.step(1/60f, 6, 2);

        // 3. turn off rendering of this object
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(2);
        int lowerY = (int)(body.getPosition().y * WikiJump.PPM / 16);
        int upperY = lowerY + 1;
        int leftX = (int)(body.getPosition().x * WikiJump.PPM / 16);
        int rightX = leftX + 1;

//        System.out.println(lowerY);
//        System.out.println(upperY);
//        System.out.println(leftX);
//        System.out.println(rightX);

        tileLayer.getCell(leftX, lowerY).setTile(null);
        tileLayer.getCell(leftX, upperY).setTile(null);
        tileLayer.getCell(rightX, lowerY).setTile(null);
        tileLayer.getCell(rightX, upperY).setTile(null);


    }
}
