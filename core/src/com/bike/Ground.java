package com.bike;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Ground extends BaseActor {

    BodyDef bodyDef;
    FixtureDef fixtureDef;
    Fixture[] groundSections;
    Sprite sprites[];
    /*Sprite sprite3;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;*/


    public Ground(World world, OrthographicCamera camera, C.Circuit circuit) {
        super(world, camera);
        /*sprite1= new Sprite(texture1);
        Texture texture2= new Texture(Gdx.files.internal("road2.png"));
        texture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite2= new Sprite(texture2);
        Texture texture3= new Texture(Gdx.files.internal("pista3.png"));
        texture3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        sprite2= new Sprite(texture2);
        sprite3= new Sprite(texture3);*/
        sprites = new Sprite[11];
        sprites[0] = new Sprite(BaseScreen.game.getManager().get("grass1.pack", TextureAtlas.class).findRegion("grass2"));
        sprites[1] = new Sprite(BaseScreen.game.getManager().get("grass1.pack", TextureAtlas.class).findRegion("grass3"));
        sprites[2] = new Sprite(BaseScreen.game.getManager().get("grass2.pack", TextureAtlas.class).findRegion("grass4"));
        sprites[3] = new Sprite(BaseScreen.game.getManager().get("grass2.pack", TextureAtlas.class).findRegion("grass5"));
        sprites[4] = new Sprite(BaseScreen.game.getManager().get("grass2.pack", TextureAtlas.class).findRegion("grass6"));
        sprites[5] = new Sprite(BaseScreen.game.getManager().get("grass2.pack", TextureAtlas.class).findRegion("grass7"));
        sprites[6] = new Sprite(BaseScreen.game.getManager().get("grass3.pack", TextureAtlas.class).findRegion("grass8"));
        sprites[7] = new Sprite(BaseScreen.game.getManager().get("grass3.pack", TextureAtlas.class).findRegion("grass9"));
        sprites[8] = new Sprite(BaseScreen.game.getManager().get("grass3.pack", TextureAtlas.class).findRegion("grass10"));
        sprites[9] = new Sprite(BaseScreen.game.getManager().get("grass3.pack", TextureAtlas.class).findRegion("grass11"));
        sprites[10] = new Sprite(BaseScreen.game.getManager().get("grass1.pack", TextureAtlas.class).findRegion("grass1"));


        PolygonShape groundShape;
        groundShape = new PolygonShape();
        groundShape.setAsBox(45f, 1.5f);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(45f, 1.5f);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        switch (circuit) {
            case ICE://ice
                fixtureDef.density = 0.92f;
                fixtureDef.restitution = 0.1f;
                fixtureDef.friction = 0.1f;
                break;
            case SAND://sand
                fixtureDef.density = 1.78f;
                fixtureDef.restitution = 0.0f;
                fixtureDef.friction = 0.8f;
                break;
            default://grass
                fixtureDef.density = 1.1f;
                fixtureDef.restitution = 0.0f;
                fixtureDef.friction = 0.6f;
        }
        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        create(new Vector2(90f, 0f));

        groundShape.dispose();

        /*mapLoader= new TmxMapLoader();
        tiledMap= mapLoader.load("groundMap.tmx");
        mapRenderer= new OrthogonalTiledMapRenderer(tiledMap);*/

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (camera.position.x < body.getPosition().x + 100f) {
            batch.draw(sprites[10], body.getPosition().x - 45f,
                    0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
            batch.draw(sprites[10], body.getPosition().x - 30f,
                    0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
            batch.draw(sprites[10], body.getPosition().x - 15f,
                    0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
            batch.draw(sprites[10], body.getPosition().x,
                    0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
            batch.draw(sprites[10], body.getPosition().x + 15f,
                    0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
            batch.draw(sprites[10], body.getPosition().x + 30f,
                    0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
        }

        for (Fixture section : groundSections) {
            if (section.getBody().getPosition().x > camera.position.x - 60f && section.getBody().getPosition().x < camera.position.x + 60f) {
                switch ((Integer) section.getUserData()) {
                    case 0:
                        batch.draw(sprites[0], section.getBody().getPosition().x,
                                0f, getOriginX(), getOriginY(), 15.1f, 4.3f, getScaleX(), getScaleY(), getRotation());
                        batch.draw(sprites[1], section.getBody().getPosition().x + 15f,
                                0f, getOriginX(), getOriginY(), 15.1f, 4.3f, getScaleX(), getScaleY(), getRotation());
                        break;
                    case 1:
                        batch.draw(sprites[2], section.getBody().getPosition().x,
                                0f, getOriginX(), getOriginY(), 15.1f, 7.9f, getScaleX(), getScaleY(), getRotation());
                        batch.draw(sprites[3], section.getBody().getPosition().x + 15f,
                                0f, getOriginX(), getOriginY(), 15.1f, 7.9f, getScaleX(), getScaleY(), getRotation());
                        break;
                    case 2:
                        batch.draw(sprites[4], section.getBody().getPosition().x,
                                0f, getOriginX(), getOriginY(), 15.1f, 5.08f, getScaleX(), getScaleY(), getRotation());
                        batch.draw(sprites[5], section.getBody().getPosition().x + 15f,
                                0f, getOriginX(), getOriginY(), 15.1f, 5.08f, getScaleX(), getScaleY(), getRotation());
                        break;

                    case 3:
                        batch.draw(sprites[6], section.getBody().getPosition().x,
                                0f, getOriginX(), getOriginY(), 15.1f, 4.53f, getScaleX(), getScaleY(), getRotation());
                        batch.draw(sprites[7], section.getBody().getPosition().x + 15f,
                                0f, getOriginX(), getOriginY(), 15.1f, 4.53f, getScaleX(), getScaleY(), getRotation());
                        break;
                    case 4:
                        batch.draw(sprites[10], section.getBody().getPosition().x,
                                0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
                        batch.draw(sprites[10], section.getBody().getPosition().x + 15f,
                                0f, getOriginX(), getOriginY(), 15.1f, 4.49f, getScaleX(), getScaleY(), getRotation());
                        break;
                    default:
                        batch.draw(sprites[8], section.getBody().getPosition().x,
                                0f, getOriginX(), getOriginY(), 15.1f, 7.99f, getScaleX(), getScaleY(), getRotation());
                        batch.draw(sprites[9], section.getBody().getPosition().x + 15f,
                                0f, getOriginX(), getOriginY(), 15.1f, 7.99f, getScaleX(), getScaleY(), getRotation());
                }
            }
        }
        /*batch.draw(sprite1, 40f,-14.5f, getOriginX(),getOriginY(), 50f, 25f, getScaleX(), getScaleY(), getRotation());
        batch.draw(sprite3, 665f,-35f, getOriginX(),getOriginY(), 335f, 43f, getScaleX(), getScaleY(), getRotation());*/
    }

    private ChainShape getShape(int shape) {
        float[] vertices;
        ChainShape sectionShape = new ChainShape();

        switch (shape) {
            case 0:
                vertices = new float[]{
                        0f, 0f,
                        30f, 0f,
                        30f, 3f,
                        28.18f, 3.14f,
                        25.93f, 3.31f,
                        23.47f, 3.49f,
                        20.54f, 3.62f,
                        17.26f, 3.72f,
                        14.62f, 3.75f,
                        10.93f, 3.74f,
                        8.1f, 3.62f,
                        7.33f, 3.54f,
                        3.25f, 3.3f,
                        0f, 3f,
                };
                break;
            case 1:
                vertices = new float[]{
                        0f, 0f,
                        30f, 0f,
                        30f, 3f,
                        27.91f, 3f,
                        26.06f, 3.21f,
                        24.48f, 3.64f,
                        23.1f, 4.44f,
                        22.12f, 5.24f,
                        21.21f, 6.04f,
                        20.12f, 6.81f,
                        18.8f, 7.39f,
                        17.521f, 7.62f,
                        16.28f, 7.62f,
                        14.43f, 7.45f,
                        13.41f, 6.99f,
                        12.59f, 6.31f,
                        11.72f, 5.52f,
                        10.62f, 4.69f,
                        9.49f, 3.98f,
                        8.31f, 3.61f,
                        6.6f, 3.41f,
                        4.41f, 3.3f,
                        0f, 3f

                };
                break;
            case 2:
                vertices = new float[]{
                        0f, 0f,
                        30f, 0f,
                        30f, 3f,
                        26.06f, 3f,
                        22.51f, 3.1f,
                        20.34f, 3.17f,
                        17.77f, 3.37f,
                        16.21f, 3.55f,
                        14.68f, 3.81f,
                        12.61f, 4.19f,
                        10.62f, 4.48f,
                        8.61f, 4.63f,
                        7.39f, 4.7f,
                        6.19f, 4.61f,
                        5.11f, 4.32f,
                        3.94f, 3.87f,
                        2.93f, 3.42f,
                        2.16f, 3.19f,
                        1.32f, 3.07f,
                        0f, 3f

                };
                break;
            case 3:
                vertices = new float[]{
                        0f, 0f,
                        30f, 0f,
                        30f, 3f,
                        28.3f, 3f,
                        26.21f, 2.77f,
                        25.19f, 2.56f,
                        23.1f, 2.22f,
                        20.84f, 1.82f,
                        18.35f, 1.55f,
                        15.86f, 1.55f,
                        13.61f, 1.81f,
                        12.02f, 2.26f,
                        10.69f, 2.77f,
                        9.39f, 3.26f,
                        7.24f, 3.7f,
                        5.6f, 3.73f,
                        3.97f, 3.64f,
                        2.58f, 3.51f,
                        1.54f, 3.33f,
                        0f, 3f

                };
                break;
            case 4:
                vertices = new float[]{
                        0f, 0f,
                        30f, 0f,
                        30f, 3f,
                        0f, 3f
                };
                break;
            default:
                vertices = new float[]{
                        0f, 0f,
                        30f, 0f,
                        30f, 3f,
                        23.09f, 3f,
                        15.73f, 3.2f,
                        13.89f, 3.51f,
                        12.73f, 4.35f,
                        11.41f, 5.49f,
                        10.24f, 6.3f,
                        8.92f, 6.83f,
                        7.53f, 7.06f,
                        6.24f, 7.06f,
                        4.75f, 6.78f,
                        3.6f, 6.07f,
                        2.74f, 5.19f,
                        2.26f, 4.6f,
                        1.61f, 3.98f,
                        1.01f, 3.54f,
                        0.44f, 3.15f,
                        0f, 3f
                };
        }
        sectionShape.createLoop(vertices);
        return sectionShape;
    }

    @Override
    public void dispose() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    @Override
    protected void create(Vector2 position) {
        groundSections = new Fixture[30];
        BodyDef section = new BodyDef();
        Random random = new Random();
        int shapeIndex;

        for (int i = 0; i < 30; i++) {
            section.position.set(position);
            shapeIndex = random.nextInt(6);
            fixtureDef.shape = getShape(shapeIndex);
            groundSections[i] = world.createBody(section).createFixture(fixtureDef);
            groundSections[i].setUserData(shapeIndex);
            position.add(30f, 0f);
        }
    }

}
