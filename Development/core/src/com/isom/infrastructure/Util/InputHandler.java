package com.isom.infrastructure.Util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.isom.infrastructure.Scene.HUD;
import com.isom.infrastructure.WikiJump;
import sprite.Direction;
import sprite.Wiki;

public class InputHandler {

    public static void handleInput(Wiki wiki) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            if (WikiJump.godMode) {
                WikiJump.godMode = false;
                HUD.getGodModeLabel().setText("");
            } else {
                WikiJump.godMode = true;
                HUD.getGodModeLabel().setText("GOD MODE!");
            }

        }


        // jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)
                || Gdx.input.isKeyJustPressed(Input.Keys.UP)
                || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            if (!wiki.jumped) wiki.jump();
//            else if (!wiki.boosted) wiki.boost();



        // left / right walk
        if ((Gdx.input.isKeyPressed(Input.Keys.A)
                || Gdx.input.isKeyPressed(Input.Keys.LEFT)))
            wiki.run(Direction.LEFT);

        if ((Gdx.input.isKeyPressed(Input.Keys.D)
                || Gdx.input.isKeyPressed(Input.Keys.RIGHT)))
            wiki.run(Direction.RIGHT);




        // shoot
        if (Gdx.input.justTouched())
            wiki.shoot();


        // god mode boost
        if (WikiJump.godMode && Gdx.input.isKeyJustPressed(Input.Keys.Q))
            wiki.boost();

    }
}
