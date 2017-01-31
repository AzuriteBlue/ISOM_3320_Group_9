package com.isom.infrastructure.Scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.WikiJump;
import sprite.Wiki;

import java.text.DecimalFormat;



public class HUD implements Disposable{

    public OrthographicCamera hudCam = new OrthographicCamera();
    private Viewport hudViewport = new FitViewport(WikiJump.V_WIDTH, WikiJump.V_HEIGHT, hudCam);
    public PlayScreen playScreen;

    private static double time;
    private static int score;
    private long startTime;
    private long currentTime;
    private DecimalFormat formatter = new DecimalFormat("####.##");

    public Stage stage;
    private Table table;
    private Label scoreTitleLabel, scoreLabel;
    private Label timeTitleLabel, timeLabel; // "cd" for countdown
    private Label posLabel;





    public double getTime() {return time;}







    public HUD(SpriteBatch batch, PlayScreen playScreen) {

        startTime = System.currentTimeMillis();
        time = 0;
        score = 0;

        this.playScreen = playScreen;
        stage = new Stage(hudViewport, batch);
        table = new Table();
        table.top();
        table.setFillParent(true);


        scoreTitleLabel = new Label("SCORE",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%6d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeTitleLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(formatter.format(time) + " s",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //table.setDebug(true);
        table.add(scoreTitleLabel).expandX().left().padLeft(20);
        table.add(timeTitleLabel).expandX().right().padRight(20);
        table.row();
        table.add(scoreLabel).left().padLeft(20);
        table.add(timeLabel).right().padRight(20);

        // TEST HARNESS
        table.row();
        posLabel = new Label("null",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(posLabel).expandX().left().padLeft(20);



        stage.addActor(table);
    }

    public void update(float delta) {


        // update time
        currentTime = System.currentTimeMillis();
        time = (double)(currentTime - startTime)/1000;

        // update the labels
        timeLabel.setText(formatter.format(time) + "s");
        scoreLabel.setText(String.format("%6d", score));


        // update posLabel
        posLabel.setText((int)(playScreen.wiki.getX() * WikiJump.PPM) +", "+ (int)(playScreen.wiki.getY() * WikiJump.PPM));

    }

    public static void addScore(int increment) {
        score += increment;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
