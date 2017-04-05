package com.aor.bouncing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.aor.bouncing.GameStage.PIXEL_TO_METER;
import static com.aor.bouncing.GameStage.VIEWPORT_WIDTH;

/**
 * The ground actor
 */

class GroundActor extends Actor {
    /**
     * The texture used to draw this actor
     */
    private final Texture texture;


    GroundActor(BouncingBalls game) {
        texture = game.getAssetManager().get("ground.png");
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    /**
     * Draws this actor
     *
     * @param batch the SpriteBatch used to draw this actor
     * @param parentAlpha the alpha component inherited from the father
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0, 0, 0, (int)(VIEWPORT_WIDTH / PIXEL_TO_METER), (int)(.50f / PIXEL_TO_METER));
    }


    /**
     * Creates the ground body
     *
     * @param world the world this body belongs to
     * @return the body
     */
    Body createBody(World world) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create the ball body
        Body body = world.createBody(bodyDef);
        body.setTransform(0, 0, 0); // Bottom left corner

        // Create rectangular shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(VIEWPORT_WIDTH, 0.5f); // Viewport width and 50cm height

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = .5f;      // how heavy is the ground
        fixtureDef.friction =  .5f;    // how slippery is the ground
        fixtureDef.restitution =  .5f; // how bouncy is the ground

        // Attach fixture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }
}
