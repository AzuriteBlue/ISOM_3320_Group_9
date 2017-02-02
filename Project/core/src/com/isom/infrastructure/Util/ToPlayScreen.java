package com.isom.infrastructure.Util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.WikiJump;

public class ToPlayScreen extends ClickListener {

    WikiJump game;

    public ToPlayScreen(WikiJump game) {
        this.game = game;
    }
    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.playScreen = new PlayScreen(game);
        game.setScreen(game.playScreen);
    }
}
