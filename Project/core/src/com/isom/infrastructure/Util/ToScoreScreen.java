package com.isom.infrastructure.Util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Screens.ScoreScreen;
import com.isom.infrastructure.WikiJump;

public class ToScoreScreen extends ClickListener {

    WikiJump game;

    public ToScoreScreen(WikiJump game) {
        this.game = game;
    }
    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.scoreScreen = new ScoreScreen(game);
        game.setScreen(game.scoreScreen);
    }
}