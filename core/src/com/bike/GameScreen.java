package com.bike;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends BaseScreen {

    World world;
    Viewport viewport;
    OrthographicCamera camera;
    Box2DDebugRenderer renderer;
    Bike bike;
    Stage stage;
    Ground ground;
    Viewport uiViewport;
    OrthographicCamera uiCamera;
    Stage uiStage;
    Button aButton, uButton, dButton;
    private boolean showed;
    //SpriteBatch spriteBatch;

    public GameScreen(BikeGame game) {
        super(game);

        //spriteBatch= new SpriteBatch();
        world = new World(new Vector2(0.0f, -9.8f), true);

        camera = new OrthographicCamera();
        viewport = new FitViewport(C.SCENE_WIDTH, C.SCENE_HEIGHT, camera);
        camera.position.set(camera.position.x + C.SCENE_WIDTH * 0.5f,
                camera.position.y + C.SCENE_HEIGHT * 0.5f, 0);
        //camera.update();

        renderer = new Box2DDebugRenderer();

        stage = new Stage(viewport);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(C.SCENE_WIDTH, C.SCENE_HEIGHT, uiCamera);
        uiCamera.position.set(uiCamera.position.x + C.SCENE_WIDTH * 0.5f,
                uiCamera.position.y + C.SCENE_HEIGHT * 0.5f, 0);
        uiStage = new Stage(uiViewport);


    }


    @Override
    public void show() {

        Gdx.input.setInputProcessor(uiStage);
        showed = true;

        Button.ButtonStyle style = new Button.ButtonStyle();
        style.down = new TextureRegionDrawable(game.getManager().get("buttondown.png", Texture.class));
        style.up = new TextureRegionDrawable(game.getManager().get("buttonup.png", Texture.class));
        aButton = new Button(style);
        aButton.setSize(1f, 1f);
        aButton.setPosition(0f, 0f);

        uButton = new Button(style);
        uButton.setSize(1f, 1f);
        uButton.setPosition(C.SCENE_WIDTH - 1.5f, 1.5f);

        dButton = new Button(style);
        dButton.setSize(1f, 1f);
        dButton.setPosition(C.SCENE_WIDTH - 1.5f, 0f);

        ground = new Ground(world, camera, C.Circuit.GRASS);
        bike = new Bike(world, camera, new Vector2(3.0f, 6.0f));
        stage.addActor(bike);
        stage.addActor(ground);
        uiStage.addActor(aButton);
        uiStage.addActor(uButton);
        uiStage.addActor(dButton);
        aButton.addCaptureListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bike.setAccelerating();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bike.setAccelerating();
            }
        });

        uButton.addCaptureListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bike.setMovingUp();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bike.setMovingUp();
            }
        });

        dButton.addCaptureListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bike.setMovingDown();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bike.setMovingDown();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.5f, 0.7f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(bike.body.getPosition().x + 4.0f, bike.body.getPosition().y - 2.0f + C.SCENE_HEIGHT * 0.5f, 0f);

        if (bike.body.getLinearVelocity().x < 2.5f && camera.zoom > 1f) {
            camera.zoom -= 0.15f * delta;
        }
        if (bike.body.getLinearVelocity().x > 2.5f && camera.zoom < 1.2f) {
            camera.zoom += 0.15f * delta;
        }

        /*if((bike.body.getPosition().y >= -2f && camera.zoom >1.0f) || (bike.body.getPosition().y <= 3f && camera.zoom <1.0f)){
            camera.zoom-= 2.0f*delta;
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom += delta * 2.0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom -= delta * 2.0f;
        }
        camera.update();


        if (bike.getFlag())
            world.step(1 / 60f, 6, 2);
        stage.act();

        stage.draw();
        uiStage.act();
        uiStage.draw();
        //renderer.render(world, viewport.getCamera().combined);
    }

    public boolean getShowed() {
        return showed;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiViewport.update(width, height);
    }

    @Override
    public void dispose() {
        ground.dispose();
        bike.dispose();
        world.dispose();
        stage.dispose();
        renderer.dispose();
    }
}
