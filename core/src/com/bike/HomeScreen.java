package com.bike;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HomeScreen extends BaseScreen {

    Button playButton;
    Stage stage;
    Viewport viewport;

    public HomeScreen(BikeGame game) {
        super(game);
        viewport = new FitViewport(C.SCENE_WIDTH, C.SCENE_HEIGHT);
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(game.getManager().get("buttonup.png", Texture.class));
        style.down = new TextureRegionDrawable(game.getManager().get("buttondown.png", Texture.class));
        playButton = new Button(style);
        playButton.setSize(2f, 1f);
        playButton.setPosition(C.SCENE_WIDTH * 0.5f - playButton.getWidth() * 0.5f, C.SCENE_HEIGHT * 0.5f);
        stage.addActor(playButton);
        playButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
