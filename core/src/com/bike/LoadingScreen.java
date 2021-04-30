package com.bike;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class LoadingScreen extends BaseScreen {
    Texture progressBarBackground, progressBar;
    Vector2 pbPosition;
    SpriteBatch batch;

    public LoadingScreen(BikeGame game) {
        super(game);
        pbPosition = new Vector2();
        batch = new SpriteBatch();
        progressBarBackground = game.getManager().get("progress_bar_base.png");
        progressBar = game.getManager().get("progress_bar.png");
        pbPosition.set((Gdx.graphics.getWidth() - progressBarBackground.getWidth()) >> 1, Gdx.graphics.getHeight() * 0.5f);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.getManager().update()) {
            game.setScreen(game.homeScreen);
            game.loadingScreen.dispose();
        }
        batch.begin();
        batch.draw(progressBarBackground, pbPosition.x, pbPosition.y);
        batch.draw(progressBar, pbPosition.x, pbPosition.y,
                progressBar.getWidth() * game.getManager().getProgress(),
                progressBar.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        progressBarBackground.dispose();
        progressBar.dispose();
        batch.dispose();
    }
}
