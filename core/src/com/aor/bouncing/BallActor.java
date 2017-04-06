package com.aor.bouncing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.aor.bouncing.GameStage.VIEWPORT_WIDTH;

/**
 * The ball actor using a Sprite to draw its texture.
 */

class BallActor extends Actor {
    /**
     * The sprite used to draw the ball.
     */
    private final Sprite sprite;

    /**
     * The animation
     */
    private final Animation<TextureRegion> animation;

    /**
     * Time used to select the current animation frame.
     */
    private float stateTime = 0;

    /**
     * Returns a ball actor.
     *
     * @param game the game the actor belongs to
     */
    BallActor(BouncingBalls game) {
        Texture texture = game.getAssetManager().get("monkey.png");

        // Split the texture into frame
        TextureRegion[][] thrustRegion = TextureRegion.split(texture, texture.getWidth() / 10, texture.getHeight());

        // Put the frames into a uni-dimensional array
        TextureRegion[] frames = new TextureRegion[10];
        System.arraycopy(thrustRegion[0], 0, frames, 0, 10);

        // Create the animation
        animation = new Animation<TextureRegion>(.25f, frames);

        sprite = new Sprite(animation.getKeyFrame(0));

        // Necessary so that inputs events are registered correctly
        setWidth(animation.getKeyFrame(0).getRegionWidth());
        setHeight(texture.getHeight());

        // Necessary so that rotations are correctly processed
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite.setOrigin(getWidth() / 2, getHeight() / 2);
    }

    /**
     * Set the position of the ball center (instead of
     * bottom-left corner)
     *
     * @param x the x-coordinate of the center
     * @param y the y-coordinate of the center
     */
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    /**
     * Updates the sprite position when the actor moves
     */
    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(), getY());
    }

    /**
     * Updates the sprite rotation when the actor rotates
     */
    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        sprite.setRotation(getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime, true));
    }

    /**
     * Draws the sprite
     *
     * @param batch the SpriteBatch used to draw the sprite
     * @param parentAlpha the alpha component inherited from the father
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setColor(getColor());
        sprite.draw(batch);
    }

    /**
     * Creates this actor body
     *
     * @param world the world this body belongs to
     * @return the body
     */
    Body createBody(World world) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Create the ball body
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        Body body = world.createBody(bodyDef);
        body.setTransform(VIEWPORT_WIDTH / 2, (VIEWPORT_WIDTH * ratio) / 2, 0); // Middle of the viewport, no rotation

        // Create circle shape
        CircleShape circle = new CircleShape();
        circle.setRadius(0.11f); // 22cm / 2

        // Create ball fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = .5f;      // how heavy is the ball
        fixtureDef.friction =  .5f;    // how slippery is the ball
        fixtureDef.restitution =  .5f; // how bouncy is the ball

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        circle.dispose();

        return body;
    }
}
