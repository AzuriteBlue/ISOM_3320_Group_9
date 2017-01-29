package com.isom.infrastructure.Scene;


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
import com.isom.infrastructure.WikiJump;

public class HUD implements Disposable{

    public OrthographicCamera hudCam = new OrthographicCamera();
    private Viewport hudViewport = new FitViewport(WikiJump.V_WIDTH, WikiJump.V_HEIGHT, hudCam);
    public Stage stage;
    Table table;

    private static Integer time;
    private static Integer score;

    Label scoreTitleLabel, scoreLabel;
    Label cdTitleLabel, cdLabel; // "cd" for countdown


    public HUD(SpriteBatch batch) {
        time = 300;
        score = 0;

        stage = new Stage(hudViewport, batch);
        table = new Table();
        table.top();
        table.setFillParent(true);


        scoreTitleLabel = new Label("SCORE",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%6d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        cdTitleLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        cdLabel = new Label(String.format("%-3d", time),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //table.setDebug(true);
        table.add(scoreTitleLabel).expandX().left().padLeft(20);
        table.add(cdTitleLabel).expandX().right().padRight(20);
        table.row();
        table.add(scoreLabel).left().padLeft(20);
        table.add(cdLabel).right().padRight(20);

        stage.addActor(table);
    }

    public static void addScore(int increment) {
        score += increment;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
