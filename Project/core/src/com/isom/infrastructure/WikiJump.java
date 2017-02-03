package com.isom.infrastructure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.isom.infrastructure.Screens.BeginScreen;
import com.isom.infrastructure.Screens.GameOverScreen;
import com.isom.infrastructure.Screens.PlayScreen;
import com.isom.infrastructure.Screens.ScoreScreen;

public class WikiJump extends Game {

	public static boolean godMode = false;

    // constants for virtual height and virtual width
	public static final int V_WIDTH = 1024;
    public static final int V_HEIGHT = 480;
    // constants for pixel per meter
    public static final float PPM = 50;

    public SpriteBatch batch;
	public static AssetManager assetManager;

    public PlayScreen playScreen;
    public BeginScreen beginScreen;
    public GameOverScreen gameOverScreen;
    public ScoreScreen scoreScreen;

	
	@Override
	public void create () {

		batch = new SpriteBatch();
		assetManager = new AssetManager();

		assetManager.load("audio/music/House.mp3", Music.class);
		assetManager.load("audio/sound/wiki-shoot.wav", Sound.class);
		assetManager.load("audio/sound/wiki-die.mp3", Sound.class);
		assetManager.load("audio/sound/wiki-spawn.mp3", Sound.class);
        assetManager.load("audio/sound/enemy-die.mp3", Sound.class);
		assetManager.load("audio/sound/picked.mp3", Sound.class);
		assetManager.load("audio/sound/win.mp3", Sound.class);
		assetManager.finishLoading();


		new Thread(new Runnable() {
			@Override
			public void run() {
				Music music = assetManager.get("audio/music/House.mp3", Music.class);
				music.setLooping(true);
				music.setVolume(0.3f);
				music.play();
			}
		}).run();


		beginScreen = new BeginScreen(this);
		setScreen(beginScreen);

//		PlayScreen playScreen  = new PlayScreen(this);
//		setScreen(playScreen);

	}

	@Override
	public void render () {
		super.render();
//		System.out.println(getScreen());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

}
