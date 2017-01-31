package com.isom.infrastructure.Util;


import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.WikiJump;
import sprite.*;

public class WorldCreator {

    private static TmxMapLoader mapLoader;
    private static TiledMap map;
    private static OrthogonalTiledMapRenderer mapRenderer;

    private static World world;


    // getters & setters
    public static TiledMap getMap() {return map;}
    public static OrthogonalTiledMapRenderer getMapRenderer() {return mapRenderer;}



    public static World createWorld() {
        // load map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ WikiJump.PPM);



        // create world and main character
        world = new World(new Vector2(0, -10), true);

        /** load tiled map object into a box2d world */



        // 1. load all ground object

        // 1.1 load all rectangle ground object
        for (RectangleMapObject rectMapObj : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = rectMapObj.getRectangle();
            BodyCreator.createRectangleBody(world, rect);
        }

        // 1.2 load all circle ground object
        for (EllipseMapObject ellipseMapObj : map.getLayers().get(3).getObjects().getByType(EllipseMapObject.class)) {

            Ellipse ellipse = ellipseMapObj.getEllipse();
            BodyCreator.createCircleBody(world, ellipse);
        }


        // 2. load other objects

        // 2.1 load all batteries
        for (RectangleMapObject rectMapObj : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = rectMapObj.getRectangle();
            new Battery(world, map, rect);
        }

        // 2.2 load all saw
        for (EllipseMapObject ellipseMapObj : map.getLayers().get(5).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ellipseMapObj.getEllipse();
            new Saw(world, map, ellipse);
        }

        // 2.3 load all ladder
        for (RectangleMapObject rectMapObj : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = rectMapObj.getRectangle();
            new Ladder(world, map, rect);
        }


        return world;
    }
    public static void createBastions(Array<Bastion> bastions, PlayScreen playScreen) {
        bastions.add(new Bastion(world, playScreen, 787, 110, 787, 940, Direction.RIGHT));
        bastions.add(new Bastion(world, playScreen, 1250, 352, 1000, 1250, Direction.LEFT));
    }
}
