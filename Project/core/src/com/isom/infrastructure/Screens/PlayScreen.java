package com.isom.infrastructure.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.EllipseShapeBuilder;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.WikiJump;
import sprite.*;


public class PlayScreen implements Screen{

    private WikiJump game;

    // cam & viewport
    private OrthographicCamera cam = new OrthographicCamera();
    private Viewport viewport = new FitViewport(game.V_WIDTH / game.PPM, game.V_HEIGHT / game.PPM,cam);

    // Tiled maps related
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // Box2D related
    private World world;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    // things requiring render
    private HUD hud;
    private Wiki wiki;








    public PlayScreen(WikiJump game) {
        this.game = game;
        hud = new HUD(game.batch);

        // load map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/ game.PPM);

        // set camera
        cam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        // create world and main character
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                // if wiki collides with something
                if (fixtureA.getUserData() == wiki || fixtureB.getUserData() == wiki) {

                    // tell which from which
                    Fixture wikiFix, otherFix;
                    if (fixtureA.getUserData() == wiki) {
                        wikiFix = fixtureA;
                        otherFix = fixtureB;
                    } else  {
                        wikiFix = fixtureB;
                        otherFix = fixtureA;
                    }


                    // call onHit() of the other object
                    if (otherFix.getUserData() instanceof Interactable) {
                        ((Interactable) otherFix.getUserData()).onHit(wiki);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        wiki = new Wiki(world);




        /** load tiled map object into a box2d world */

        // 0. preparation
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        CircleShape circleShape = new CircleShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        // 1. load all ground object

        // 1.1 load all rectangle ground object
        for (RectangleMapObject rectMapObj : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = rectMapObj.getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            float x = (rect.getX() + rect.getWidth() / 2) / game.PPM;
            float y = (rect.getY() + rect.getHeight() / 2) / game.PPM;
            bodyDef.position.set(x, y);

            body = world.createBody(bodyDef);

            polygonShape.setAsBox((rect.getWidth() / 2)/game.PPM, (rect.getHeight() / 2)/game.PPM);
            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }

        // 1.2 load all circle ground object
        for (EllipseMapObject ellipseMapObj : map.getLayers().get(3).getObjects().getByType(EllipseMapObject.class)) {

            Ellipse ellipse = ellipseMapObj.getEllipse();

            float x = (ellipse.x + ellipse.height/2) / game.PPM;
            float y = (ellipse.y+ ellipse.height/2) /game.PPM;
            float radius = (ellipse.height/2) / game.PPM;
            Circle circle = new Circle(x, y, radius);

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(circle.x, circle.y);

            body = world.createBody(bodyDef);

            circleShape.setRadius(circle.radius);
            fixtureDef.shape = circleShape;
            body.createFixture(fixtureDef);
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

    }






    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        // 1. set screen as black
        Gdx.gl.glClearColor(0, 0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 2. render the map
        mapRenderer.setView(cam);
        mapRenderer.render();

        // 3. render the main character
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        wiki.draw(game.batch);
        game.batch.end();



        // 4. render the HUD
        game.batch.setProjectionMatrix(hud.hudCam.combined);
        hud.stage.draw();

        // 5. open debug renderer
        debugRenderer.render(world, cam.combined);

    }

    public void update(float delta) {

        // handle input
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP))
            wiki.body.applyLinearImpulse(new Vector2(0, 4f), wiki.body.getWorldCenter(), true);
        if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) && wiki.body.getLinearVelocity().x >= -2)
            wiki.body.applyLinearImpulse(new Vector2(-0.1f, 0), wiki.body.getWorldCenter(), true);
        if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && wiki.body.getLinearVelocity().x <= 2)
            wiki.body.applyLinearImpulse(new Vector2(0.1f, 0), wiki.body.getWorldCenter(), true);


        cam.position.x = wiki.body.getPosition().x;
        //cam.position.y = wiki.body.getPosition().y;
        //System.out.println(cam.position.x);

        // update sprite with body position
        wiki.update(delta);

        world.step(1/60f, 6, 2);

        // update camera
        cam.update();
        // update mapRenderer's rendering view
        mapRenderer.setView(cam);

        if (wiki.body.getPosition().y < 0) game.create();



    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }
}
