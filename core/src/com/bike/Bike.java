package com.bike;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class Bike extends BaseActor {

    boolean flag = true;
    boolean accelerating, movingUp, movingDown;
    Body frontWheel;
    Fixture frontWheelFixture;
    Body backWheel;
    Fixture backWheelFixture;
    Sprite[] bikeSprites;

    /*Body frontDamper;
    Fixture frontDamperFixture;*/
    Body backDamper;
    Fixture backDamperFixture;

    Body torso;
    Fixture torsoFixture;

    Body arm;
    Fixture armFixture;
    Body foreArm;
    Fixture foreArmFixture;

    Body leg;
    Fixture legFixture;
    Body foot;
    Fixture footFixture;

    Body head;
    Fixture headFixture;

    public Bike(World world, OrthographicCamera camera, Vector2 position) {
        super(world, camera);
        create(position);
        bikeSprites = new Sprite[]{
                new Sprite(BaseScreen.game.getManager().get("moto.pack", TextureAtlas.class).findRegion("brake")),
                new Sprite(BaseScreen.game.getManager().get("moto.pack", TextureAtlas.class).findRegion("wheel")),
                new Sprite(BaseScreen.game.getManager().get("moto.pack", TextureAtlas.class).findRegion("moto")),
                new Sprite(BaseScreen.game.getManager().get("moto.pack", TextureAtlas.class).findRegion("frontdamper")),
                new Sprite(BaseScreen.game.getManager().get("moto.pack", TextureAtlas.class).findRegion("backdamper")),
                new Sprite(BaseScreen.game.getManager().get("body.pack", TextureAtlas.class).findRegion("head")),
                new Sprite(BaseScreen.game.getManager().get("body.pack", TextureAtlas.class).findRegion("torso")),
                new Sprite(BaseScreen.game.getManager().get("body.pack", TextureAtlas.class).findRegion("arm")),
                new Sprite(BaseScreen.game.getManager().get("body.pack", TextureAtlas.class).findRegion("forearm")),
                new Sprite(BaseScreen.game.getManager().get("body.pack", TextureAtlas.class).findRegion("leg")),
                new Sprite(BaseScreen.game.getManager().get("body.pack", TextureAtlas.class).findRegion("foot"))
        };
        bikeSprites[0].setSize(0.3f, 0.3f);
        bikeSprites[0].setOrigin(0.15f, 0.15f);

        bikeSprites[1].setSize(0.6f, 0.6f);
        bikeSprites[1].setOrigin(bikeSprites[1].getWidth() * 0.5f, bikeSprites[1].getHeight() * 0.5f);

        bikeSprites[2].setSize(1.78f, 0.89f);
        bikeSprites[2].setOrigin(0.76f, 0.1f);

        bikeSprites[3].setSize(0.24f, 0.35f);
        bikeSprites[3].setOrigin(0.2f, 0.01f);

        bikeSprites[4].setSize(0.5f, 0.16f);
        bikeSprites[4].setOrigin(0.25f, 0.08f);

        bikeSprites[5].setSize(0.32f, 0.3f);
        bikeSprites[5].setOrigin(0.15f, 0.15f);

        bikeSprites[6].setSize(0.25f, 0.62f);
        bikeSprites[6].setOrigin(0.125f, 0.25f);

        bikeSprites[7].setSize(0.1f, 0.3f);
        bikeSprites[7].setOrigin(0.05f, 0.15f);

        bikeSprites[8].setSize(0.1f, 0.2f);
        bikeSprites[8].setOrigin(0.05f, 0.1f);

        bikeSprites[9].setSize(0.16f, 0.4f);
        bikeSprites[9].setOrigin(0.08f, 0.18f);

        bikeSprites[10].setSize(0.24f, 0.32f);
        bikeSprites[10].setOrigin(0.06f, 0.15f);

    }

    @Override
    protected void create(Vector2 position) {

        //BOX
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.position.set(0.5f, 6.0f);
        boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.4f, 0.5f);

        FixtureDef boxFixtureDef = new FixtureDef();
        boxFixtureDef.shape = boxShape;
        boxFixtureDef.density = 70.0f;
        boxFixtureDef.friction = 10.0f;

        Body boxBody = world.createBody(boxBodyDef);
        boxBody.createFixture(boxFixtureDef);
        boxShape.dispose();

        createFrame(position);
        createFrontWheel(position);
        //createFrontDamper(position);
        createBackDamper(position);
        createBackWheel(position);
        createTorso(position);
        createArm(position);
        createLeg(position);
        createHead(position);

        WheelJointDef frontWheelJointDef = new WheelJointDef();
        frontWheelJointDef.bodyA = body;
        frontWheelJointDef.bodyB = frontWheel;
        frontWheelJointDef.localAnchorA.set(0.86f, -0.07f);
        frontWheelJointDef.localAnchorB.set(0f, 0f);
        frontWheelJointDef.dampingRatio = 0.6f;
        frontWheelJointDef.frequencyHz = 8f;
        frontWheelJointDef.localAxisA.set(MathUtils.cos(-62 * MathUtils.degreesToRadians), MathUtils.sin(-62 * MathUtils.degreesToRadians));
        world.createJoint(frontWheelJointDef);

        WheelJointDef backWheelJointDef = new WheelJointDef();
        backWheelJointDef.bodyA = body;
        backWheelJointDef.bodyB = backWheel;
        backWheelJointDef.localAnchorA.set(-0.48f, -0.13f);
        backWheelJointDef.localAnchorB.set(0f, 0f);
        backWheelJointDef.dampingRatio = 0.6f; //How many oscillations
        backWheelJointDef.frequencyHz = 8f; //How much oscillates
        backWheelJointDef.localAxisA.set(MathUtils.cos(-62 * MathUtils.degreesToRadians), MathUtils.sin(-62 * MathUtils.degreesToRadians));
        world.createJoint(backWheelJointDef);

        RevoluteJointDef torsoJointDef1 = new RevoluteJointDef();
        torsoJointDef1.bodyA = body;
        torsoJointDef1.bodyB = torso;
        torsoJointDef1.localAnchorA.set(0f, 0.46f);
        torsoJointDef1.localAnchorB.set(0f, -0.25f);
        world.createJoint(torsoJointDef1);

        RopeJointDef torsoJointDef2 = new RopeJointDef();
        torsoJointDef2.bodyA = body;
        torsoJointDef2.bodyB = torso;
        torsoJointDef2.localAnchorA.set(0.5f * MathUtils.cos(75f * MathUtils.degreesToRadians), 1f);
        torsoJointDef2.localAnchorB.set(0f, 0.25f);
        torsoJointDef2.maxLength = 0.1f;
        world.createJoint(torsoJointDef2);

        RopeJointDef headJointDef1 = new RopeJointDef();
        headJointDef1.bodyA = torso;
        headJointDef1.bodyB = head;
        headJointDef1.localAnchorA.set(0f, 0.25f);
        headJointDef1.localAnchorB.set(-0.15f * MathUtils.cos(75f * MathUtils.degreesToRadians), -0.15f * MathUtils.sin(75f * MathUtils.degreesToRadians));
        headJointDef1.maxLength = 0.02f;
        world.createJoint(headJointDef1);

        RopeJointDef headJointDef2 = new RopeJointDef();
        headJointDef2.bodyA = torso;
        headJointDef2.bodyB = head;
        headJointDef2.localAnchorA.set(0f, 0.59f);
        headJointDef2.localAnchorB.set(0.15f * MathUtils.cos(75f * MathUtils.degreesToRadians), 0.15f * MathUtils.sin(75f * MathUtils.degreesToRadians));
        headJointDef2.maxLength = 0.02f;
        world.createJoint(headJointDef2);

        RevoluteJointDef armJointDef = new RevoluteJointDef();
        armJointDef.bodyA = torso;
        armJointDef.bodyB = arm;
        armJointDef.localAnchorA.set(0f, 0.25f);
        armJointDef.localAnchorB.set(0f, 0.15f);
        armJointDef.enableLimit = true;
        armJointDef.lowerAngle = -45f * MathUtils.degreesToRadians;
        armJointDef.upperAngle = 45f * MathUtils.degreesToRadians;
        world.createJoint(armJointDef);

        RevoluteJointDef foreArmJointDef = new RevoluteJointDef();
        foreArmJointDef.bodyA = arm;
        foreArmJointDef.bodyB = foreArm;
        foreArmJointDef.localAnchorA.set(0f, -0.15f);
        foreArmJointDef.localAnchorB.set(0f, 0.1f);
        foreArmJointDef.enableLimit = true;
        foreArmJointDef.lowerAngle = -45f * MathUtils.degreesToRadians;
        foreArmJointDef.upperAngle = 45f * MathUtils.degreesToRadians;
        world.createJoint(foreArmJointDef);

        RopeJointDef foreArmJointDef2 = new RopeJointDef();
        foreArmJointDef2.bodyA = body;
        foreArmJointDef2.bodyB = foreArm;
        foreArmJointDef2.localAnchorA.set(0.2f, 0.46f);
        foreArmJointDef2.localAnchorB.set(0f, 0.1f);
        foreArmJointDef2.maxLength = 0.3f;
        world.createJoint(foreArmJointDef2);

        RevoluteJointDef handsJointDef = new RevoluteJointDef();
        handsJointDef.bodyA = foreArm;
        handsJointDef.bodyB = body;
        handsJointDef.localAnchorA.set(0f, -0.1f);
        handsJointDef.localAnchorB.set(0.4f, 0.7f);
        handsJointDef.enableLimit = true;
        handsJointDef.lowerAngle = -45f * MathUtils.degreesToRadians;
        handsJointDef.upperAngle = 45f * MathUtils.degreesToRadians;
        world.createJoint(handsJointDef);

        RevoluteJointDef backDamperJointDef1 = new RevoluteJointDef();
        backDamperJointDef1.bodyA = body;
        backDamperJointDef1.bodyB = backDamper;
        backDamperJointDef1.localAnchorA.set(0f, 0f);
        backDamperJointDef1.localAnchorB.set(0.25f, 0f);
        world.createJoint(backDamperJointDef1);

        RevoluteJointDef backDamperJointDef2 = new RevoluteJointDef();
        backDamperJointDef2.bodyA = backDamper;
        backDamperJointDef2.bodyB = backWheel;
        backDamperJointDef2.localAnchorA.set(-0.25f, 0f);
        backDamperJointDef2.localAnchorB.set(0f, 0f);
        world.createJoint(backDamperJointDef2);

        RevoluteJointDef legJointDef = new RevoluteJointDef();
        legJointDef.bodyA = torso;
        legJointDef.bodyB = leg;
        legJointDef.localAnchorA.set(0f, -0.25f);
        legJointDef.localAnchorB.set(0f, 0.16f);
        world.createJoint(legJointDef);

        RevoluteJointDef footJointDef = new RevoluteJointDef();
        footJointDef.bodyA = leg;
        footJointDef.bodyB = foot;
        footJointDef.localAnchorA.set(0f, -0.16f);
        footJointDef.localAnchorB.set(0f, 0.15f);
        world.createJoint(footJointDef);

        RevoluteJointDef footJointDef2 = new RevoluteJointDef();
        footJointDef2.bodyA = body;
        footJointDef2.bodyB = foot;
        footJointDef2.localAnchorA.set(0.2f, 0f);
        footJointDef2.localAnchorB.set(0f, -0.15f);
        world.createJoint(footJointDef2);
    }

    private void createFrame(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape shape = new PolygonShape();
        float[] vertices = {
                0f, 0f,
                0.3f, 0f,
                0.62f, 0.46f,
                -0.53f, 0.46f
        };
        shape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 50.0f;
        fixtureDef.filter.groupIndex = C.BIKE_GROUP;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    private void createFrontWheel(Vector2 position) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x + 0.86f, position.y - 0.07f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 15.0f;
        fixtureDef.friction = 1.0f;
        fixtureDef.filter.groupIndex = C.BIKE_GROUP;

        frontWheel = world.createBody(bodyDef);
        frontWheelFixture = frontWheel.createFixture(fixtureDef);

        shape.dispose();
    }

    private void createBackWheel(Vector2 position) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x - 0.48f, position.y - 0.13f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 15.0f;
        fixtureDef.friction = 1.0f;
        //fixtureDef.restitution=2.5f;
        fixtureDef.filter.groupIndex = C.BIKE_GROUP;

        backWheel = world.createBody(bodyDef);
        backWheelFixture = backWheel.createFixture(fixtureDef);

        shape.dispose();
    }


    private void createBackDamper(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x - 0.24f, position.y - 0.06f);
        bodyDef.angle = 15.f * MathUtils.degreesToRadians;
        //bodyDef.fixedRotation=true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.25f, 0.08f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 3.0f;
        fixtureDef.filter.groupIndex = C.BIKE_GROUP;

        backDamper = world.createBody(bodyDef);
        backDamperFixture = backDamper.createFixture(fixtureDef);

        shape.dispose();
    }

    private void createTorso(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x + 0.25f * MathUtils.cos(75f * MathUtils.degreesToRadians),
                position.y + 0.46f + 0.25f * MathUtils.sin(75f * MathUtils.degreesToRadians));
        bodyDef.angle = -15.0f * MathUtils.degreesToRadians;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.fixedRotation=true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.12f, 0.25f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10.0f;
        fixtureDef.filter.groupIndex = C.BIKE_GROUP;

        torso = world.createBody(bodyDef);
        torsoFixture = torso.createFixture(fixtureDef);

        shape.dispose();
    }

    private void createArm(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x + 0.5f * MathUtils.cos(75f * MathUtils.degreesToRadians),
                position.y - 0.15f + 0.46f + 0.5f * MathUtils.sin(75f * MathUtils.degreesToRadians));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.fixedRotation=true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.05f, 0.15f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4.0f;
        fixtureDef.filter.groupIndex = C.BIKE_GROUP;

        arm = world.createBody(bodyDef);
        armFixture = arm.createFixture(fixtureDef);

        shape.dispose();

        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.position.set(position.x + 0.1f * MathUtils.cos(45f * MathUtils.degreesToRadians) + 0.5f * MathUtils.cos(75f * MathUtils.degreesToRadians),
                position.y + 0.46f - 0.1f * MathUtils.sin(45f * MathUtils.degreesToRadians) + 0.5f * MathUtils.sin(75f * MathUtils.degreesToRadians) - 0.3f);
        bodyDef1.angle = 45.0f * MathUtils.degreesToRadians;
        bodyDef1.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.fixedRotation=true;
        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(0.05f, 0.1f);

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = shape1;
        fixtureDef1.density = 4.0f;
        fixtureDef1.filter.groupIndex = C.BIKE_GROUP;

        foreArm = world.createBody(bodyDef1);
        foreArmFixture = foreArm.createFixture(fixtureDef1);

        shape1.dispose();
    }

    private void createLeg(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x + 0.16f * MathUtils.cos(45f * MathUtils.degreesToRadians),
                position.y + 0.46f - 0.16f * MathUtils.sin(45f * MathUtils.degreesToRadians));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.angle = 45f * MathUtils.degreesToRadians;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.08f, 0.16f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4.0f;
        fixtureDef.filter.groupIndex = C.BIKE_GROUP;

        leg = world.createBody(bodyDef);
        legFixture = leg.createFixture(fixtureDef);

        shape.dispose();

        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.position.set(position.x - 0.15f * MathUtils.cos(75f * MathUtils.degreesToRadians) + 0.32f * MathUtils.cos(45f * MathUtils.degreesToRadians),
                position.y + 0.46f - 0.15f * MathUtils.sin(75f * MathUtils.degreesToRadians) - 0.32f * MathUtils.sin(45f * MathUtils.degreesToRadians));
        bodyDef1.type = BodyDef.BodyType.DynamicBody;
        bodyDef1.angle = -15f * MathUtils.degreesToRadians;
        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(0.06f, 0.15f);

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = shape1;
        fixtureDef1.density = 4.0f;
        fixtureDef1.filter.groupIndex = C.BIKE_GROUP;

        foot = world.createBody(bodyDef1);
        footFixture = foot.createFixture(fixtureDef1);

        shape1.dispose();
    }

    private void createHead(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x + 0.67f * MathUtils.cos(75f * MathUtils.degreesToRadians),
                position.y + 0.46f + 0.67f * MathUtils.sin(75f * MathUtils.degreesToRadians));
        //bodyDef.angle= -15f*MathUtils.degreesToRadians;
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        CircleShape shape = new CircleShape();
        shape.setRadius(0.15f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4.0f;
        //fixtureDef.friction= 2.0f;
        //fixtureDef.restitution=2.5f;

        head = world.createBody(bodyDef);
        headFixture = head.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void dispose() {
        body.destroyFixture(fixture);
        frontWheel.destroyFixture(frontWheelFixture);
        //frontDamper.destroyFixture(frontDamperFixture);
        //backDamper.destroyFixture(backDamperFixture);
        backWheel.destroyFixture(backWheelFixture);

        world.destroyBody(body);
        //world.destroyBody(frontDamper);
        world.destroyBody(frontWheel);
        //world.destroyBody(backDamper);
        world.destroyBody(backWheel);
    }

    public void setAccelerating() {
        this.accelerating = !accelerating;
    }

    public void setMovingUp() {
        this.movingUp = !movingUp;
    }

    public void setMovingDown() {
        this.movingDown = !movingDown;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(bikeSprites[0], frontWheel.getPosition().x - 0.15f, frontWheel.getPosition().y - 0.15f, bikeSprites[0].getOriginX(), bikeSprites[0].getOriginY(),
                bikeSprites[0].getWidth(), bikeSprites[0].getHeight(), getScaleX(), getScaleY(), body.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[0], backWheel.getPosition().x - 0.15f, backWheel.getPosition().y - 0.15f, bikeSprites[0].getOriginX(), bikeSprites[0].getOriginY(),
                bikeSprites[0].getWidth(), bikeSprites[0].getHeight(), getScaleX(), getScaleY(), body.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[1], frontWheel.getPosition().x - 0.3f, frontWheel.getPosition().y - 0.3f, bikeSprites[1].getOriginX(), bikeSprites[1].getOriginY(),
                bikeSprites[1].getWidth(), bikeSprites[1].getHeight(), getScaleX(), getScaleY(), frontWheel.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[1], backWheel.getPosition().x - 0.3f, backWheel.getPosition().y - 0.3f, bikeSprites[1].getOriginX(), bikeSprites[1].getOriginY(),
                bikeSprites[1].getWidth(), bikeSprites[1].getHeight(), getScaleX(), getScaleY(), backWheel.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[2], body.getPosition().x - 0.76f, body.getPosition().y - 0.1f, bikeSprites[2].getOriginX(), bikeSprites[2].getOriginY(),
                bikeSprites[2].getWidth(), bikeSprites[2].getHeight(), getScaleX(), getScaleY(), body.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[3], frontWheel.getPosition().x - 0.23f, frontWheel.getPosition().y - 0.02f, bikeSprites[3].getOriginX(), bikeSprites[3].getOriginY(),
                bikeSprites[3].getWidth(), bikeSprites[3].getHeight(), getScaleX(), getScaleY(), body.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[4], backDamper.getPosition().x - 0.25f, backDamper.getPosition().y - 0.08f, bikeSprites[4].getOriginX(), bikeSprites[4].getOriginY(),
                bikeSprites[4].getWidth(), bikeSprites[4].getHeight(), getScaleX(), getScaleY(), backDamper.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[6], torso.getPosition().x - 0.125f, torso.getPosition().y - 0.25f, bikeSprites[6].getOriginX(), bikeSprites[6].getOriginY(),
                bikeSprites[6].getWidth(), bikeSprites[6].getHeight(), getScaleX(), getScaleY(), torso.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[5], head.getPosition().x - 0.15f, head.getPosition().y - 0.15f, bikeSprites[5].getOriginX(), bikeSprites[5].getOriginY(),
                bikeSprites[5].getWidth(), bikeSprites[5].getHeight(), getScaleX(), getScaleY(), head.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[7], arm.getPosition().x - 0.05f, arm.getPosition().y - 0.15f, bikeSprites[7].getOriginX(), bikeSprites[7].getOriginY(),
                bikeSprites[7].getWidth(), bikeSprites[7].getHeight(), getScaleX(), getScaleY(), arm.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[8], foreArm.getPosition().x - 0.05f, foreArm.getPosition().y - 0.1f, bikeSprites[8].getOriginX(), bikeSprites[8].getOriginY(),
                bikeSprites[8].getWidth(), bikeSprites[8].getHeight(), getScaleX(), getScaleY(), foreArm.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[9], leg.getPosition().x - 0.08f, leg.getPosition().y - 0.18f, bikeSprites[9].getOriginX(), bikeSprites[9].getOriginY(),
                bikeSprites[9].getWidth(), bikeSprites[9].getHeight(), getScaleX(), getScaleY(), leg.getAngle() * MathUtils.radiansToDegrees);

        batch.draw(bikeSprites[10], foot.getPosition().x - 0.06f, foot.getPosition().y - 0.15f, bikeSprites[10].getOriginX(), bikeSprites[10].getOriginY(),
                bikeSprites[10].getWidth(), bikeSprites[10].getHeight(), getScaleX(), getScaleY(), foot.getAngle() * MathUtils.radiansToDegrees);

    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public void act(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            flag = !flag;
        }
        if ((accelerating) && backWheel.getAngularVelocity() > -80.0f) {
            backWheel.applyTorque(-20.0f, true);
        }
        if (movingUp) {
            torso.applyTorque(20f, true);
        }


        if (movingDown) {
            torso.applyTorque(-20f, true);

        }


    }
}
