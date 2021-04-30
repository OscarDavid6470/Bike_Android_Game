package com.bike;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public abstract class BaseActor extends Actor implements Disposable {

    protected World world;
    protected Body body;
    protected Fixture fixture;
    protected OrthographicCamera camera;

    public BaseActor(World world, OrthographicCamera camera) {
        this.world = world;
        this.camera = camera;
    }

    protected abstract void create(Vector2 position);
}
