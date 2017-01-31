package com.isom.infrastructure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.isom.infrastructure.Screens.BeginScreen;
import com.isom.infrastructure.Screens.GameOverScreen;
import com.isom.infrastructure.Screens.PlayScreen;

public class WikiJump extends Game {

    // constants for virtual height and virtual width
	public static final int V_WIDTH = 1024;
    public static final int V_HEIGHT = 480;
    // constants for pixel per meter
    public static final float PPM = 50;

    public SpriteBatch batch;
	public AssetManager assetManager;

    public PlayScreen playScreen;
    public BeginScreen beginScreen;
    public GameOverScreen gameOverScreen;

	
	@Override
	public void create () {

		batch = new SpriteBatch();
		assetManager = new AssetManager();

		assetManager.load("Audio/Music/Reveries.mp3", Music.class);
		assetManager.finishLoading();

		playScreen = new PlayScreen(this);
		setScreen(playScreen);

//		beginScreen = new BeginScreen(this);
//		setScreen(beginScreen);
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
