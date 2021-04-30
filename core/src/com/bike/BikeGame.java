package com.bike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class BikeGame extends Game {

	protected HomeScreen homeScreen;
	protected LoadingScreen loadingScreen;
	protected GameScreen gameScreen;
	private AssetManager manager;

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("progress_bar_base.png", Texture.class);
		manager.load("progress_bar.png", Texture.class);
		manager.finishLoading();
		manager.load("grass1.pack", TextureAtlas.class);
		manager.load("grass2.pack", TextureAtlas.class);
		manager.load("grass3.pack", TextureAtlas.class);
		manager.load("moto.pack", TextureAtlas.class);
		manager.load("body.pack", TextureAtlas.class);
		manager.load("buttonup.png", Texture.class);
		manager.load("buttondown.png", Texture.class);


		loadingScreen = new LoadingScreen(this);
		homeScreen = new HomeScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(loadingScreen);
	}

	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void dispose() {
		manager.dispose();
		homeScreen.dispose();
		if (gameScreen.getShowed()) {
			gameScreen.dispose();
		}
	}
}
