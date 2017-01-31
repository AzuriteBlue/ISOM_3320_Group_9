package com.isom.infrastructure.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isom.infrastructure.WikiJump;

public class BeginScreen implements Screen {

    public WikiJump game;
    private OrthographicCamera cam = new OrthographicCamera();
    private Viewport viewport;

    // Scene2D
    Stage stage;
    Table table;
    Label titleLabel;

    TextButton startButton;
    Label startLabel;
    Button helpButton;
    Label helpLabel;
    Button highScoreButton;
    Label highScoreLabel;

    // bitmap font
    BitmapFont aboveFont;
    BitmapFont aboveFontSmall;



    public BeginScreen(WikiJump game) {
        aboveFont = new BitmapFont(Gdx.files.internal("fonts/above.fnt")); //Gdx.files.internal("fonts/above_0.tga")
        aboveFont.getData().setScale(1.1f, 1);

        aboveFontSmall = new BitmapFont(Gdx.files.internal("fonts/above.fnt")); //Gdx.files.internal("fonts/above_0.tga")
        aboveFontSmall.getData().setScale(0.55f, 0.5f);

        this.game = game;
        viewport = new FitViewport(game.V_WIDTH / 0.4f, game.V_HEIGHT / 0.4f);

        stage = new Stage(viewport, game.batch);
        table = new Table();
        table.top();
        table.setFillParent(true);
        //table.setDebug(true);

        titleLabel = new Label("<WIKIJUMP />", new Label.LabelStyle(aboveFont, Color.WHITE));
        table.add(titleLabel).expandX().padTop(120);
        table.row();

        aboveFont.getData().setScale(0.66f, 0.6f);
        TextButton.TextButtonStyle startStyle = new TextButton.TextButtonStyle();
        startStyle.font = aboveFontSmall;
        startButton = new TextButton("START", startStyle);
        startButton.setTouchable(Touchable.enabled);
//        startButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("I got clicked!");
//            }
//        });





        table.add(startButton).padTop(150);

        stage.addActor(table);


    }





    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // 1. set screen as black
        Gdx.gl.glClearColor(0.9f, 0.9f,0.9f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 2. render the stage
        //game.batch.setProjectionMatrix(cam.combined);
        stage.draw();



    }

    @Override
    public void resize(int width, int height) {

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
