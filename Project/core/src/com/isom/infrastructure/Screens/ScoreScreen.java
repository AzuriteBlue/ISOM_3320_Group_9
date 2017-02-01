package com.isom.infrastructure.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.WikiJump;


public class ScoreScreen implements Screen {

    public WikiJump game;
    private OrthographicCamera cam = new OrthographicCamera();
    private Viewport viewport;


    // Scene2D
    Stage stage;
    Table table;
    Skin skin;

    Label titleLabel;
    Label scoreLabel1;
    Label scoreLabel2;
    Label scoreLabel3;

    TextButton restartButton;
    TextButton exitButton;





    // bitmap font
    BitmapFont aboveFont;
    BitmapFont captainFont;



    public ScoreScreen(WikiJump game) {

        aboveFont = new BitmapFont(Gdx.files.internal("fonts/above/above.fnt")); //Gdx.files.internal("fonts/above_0.tga")
        aboveFont.getData().setScale(1.3f, 1.3f);

        captainFont = new BitmapFont(Gdx.files.internal("fonts/captain/captain.fnt"));
        captainFont.getData().setScale(1.3f, 1.3f);

        // test
        skin = new Skin(Gdx.files.internal("skin/skin/flat-earth-ui.json"));

        this.game = game;
        viewport = new FitViewport(game.V_WIDTH/0.2f , game.V_HEIGHT/0.2f, cam);
        viewport.apply();

        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();


        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

    }





    @Override
    public void show() {
        table = new Table();
        table.top();
        table.setFillParent(true);
        //table.setDebug(true);


        titleLabel = new Label("HIGH SCORE", new Label.LabelStyle(captainFont, Color.BLACK));
        scoreLabel1 = new Label("NO.1: "+Integer.toString(HUD.getHighScore(0)), new Label.LabelStyle(captainFont, Color.BLACK));
        scoreLabel2 = new Label("NO.1: "+Integer.toString(HUD.getHighScore(1)), new Label.LabelStyle(captainFont, Color.BLACK));
        scoreLabel3 = new Label("NO.1: "+Integer.toString(HUD.getHighScore(2)), new Label.LabelStyle(captainFont, Color.BLACK));


        restartButton = new TextButton("RESTART", skin);
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playScreen = new PlayScreen(game);
                game.setScreen(game.playScreen);
            }
        });
        restartButton.center();



        exitButton = new TextButton("EXIT", skin);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        exitButton.center();



        table.add(titleLabel).expandX().padTop(70);
        table.row();
        table.add(scoreLabel1).expandX().padTop(70);
        table.row();
        table.add(scoreLabel2).expandX().padTop(70);
        table.row();
        table.add(scoreLabel3).expandX().padTop(70);
        table.row();

        table.add(restartButton).padTop(200);
        table.row();
        table.add(exitButton).padTop(200);

        stage.addActor(table);


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.9f, 0.9f,0.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
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

    }
}
