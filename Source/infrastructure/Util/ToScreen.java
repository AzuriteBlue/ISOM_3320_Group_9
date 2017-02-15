package com.isom.infrastructure.Util;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.isom.infrastructure.Screens.*;
import com.isom.infrastructure.WikiJump;

public class ToScreen extends ClickListener{
    public enum ScreenType {
        BEGIN, HELP, PLAY, SCORE
    }
    ScreenType screenType;
    WikiJump game;

    public ToScreen(WikiJump game, ScreenType screenType) {
        this.game = game;
        this.screenType = screenType;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {

        switch (screenType) {
            case BEGIN: {
                game.beginScreen = new BeginScreen(game);
                game.setScreen(game.beginScreen);
                break;
            }

            case HELP: {
                game.helpScreen = new HelpScreen(game);
                game.setScreen(game.helpScreen);
                break;
            }

            case PLAY: {
                game.playScreen = new PlayScreen(game);
                game.setScreen(game.playScreen);
                break;
            }

            case SCORE: {
                game.scoreScreen = new ScoreScreen(game);
                game.setScreen(game.scoreScreen);
                break;
            }
        }

    }
}
