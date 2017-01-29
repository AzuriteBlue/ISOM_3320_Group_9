package com.isom.infrastructure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.isom.infrastructure.Screens.PlayScreen;

public class WikiJump extends Game {

    // constants for virtual height and virtual width
	public static final int V_WIDTH = 1024;
    public static final int V_HEIGHT = 480;
    // constants for pixel per meter
    public static final float PPM = 50;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
