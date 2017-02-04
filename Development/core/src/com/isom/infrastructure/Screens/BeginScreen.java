package com.isom.infrastructure.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isom.infrastructure.Util.ToScreen;
import com.isom.infrastructure.WikiJump;

public class BeginScreen implements Screen {

    public WikiJump game;
    private OrthographicCamera cam = new OrthographicCamera();
    private Viewport viewport;



    // Scene2D
    Stage stage;
    Table table;
    Label titleLabel;
    Skin skin;

    TextButton startButton;
    TextButton helpButton;


    // bitmap font
    BitmapFont aboveFont;
    BitmapFont captainFont;




    public BeginScreen(WikiJump game) {

        aboveFont = new BitmapFont(Gdx.files.internal("fonts/above/above.fnt"));
        aboveFont.getData().setScale(1.3f, 1.3f);

        captainFont = new BitmapFont(Gdx.files.internal("fonts/captain/captain.fnt"));
        captainFont.getData().setScale(1.3f, 1.3f);


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


        titleLabel = new Label("<WIKIJUMP />", new Label.LabelStyle(aboveFont, Color.WHITE));

        startButton = new TextButton("START", skin);
        startButton.addListener(new ToScreen(game, ToScreen.ScreenType.PLAY));
        startButton.center();




        helpButton = new TextButton("HELP", skin);
        helpButton.addListener(new ToScreen(game, ToScreen.ScreenType.HELP));
        helpButton.center();



        table.add(titleLabel).expandX().padTop(200);
        table.row();
        table.add(startButton).padTop(1200);
        table.row();
        table.add(helpButton).padTop(200);


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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        aboveFont.dispose();
        captainFont.dispose();

    }
}
