package com.aor.bouncing;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * The game stage
 */

class GameStage extends Stage {
    /**
     * Viewport width in meters. Height depends on screen ratio
     */
    static final int VIEWPORT_WIDTH = 4;

    /**
     * A football is 22cm in diameter and the sprite has a width of 200px
     */
    static final float PIXEL_TO_METER = 0.22f / 200;

    /**
     * The physical world
     */
    private final World world;

    /**
     * The ball body
     */
    private final Body ballBody;

    /**
     * The ball actor
     */
    private final BallActor ballActor;

    /**
     * The sound the ball makes when kicked.
     */
    private final Sound kickSound;

    GameStage(BouncingBalls game) {
        // Set the viewport
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        setViewport(new FitViewport(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio));

        // Load the textures
        game.getAssetManager().load("ball.png", Texture.class);
        game.getAssetManager().load("ground.png", Texture.class);
        game.getAssetManager().load("kick.wav", Sound.class);
        game.getAssetManager().load("music.mp3", Music.class);
        game.getAssetManager().finishLoading();

        ballActor = new BallActor(game);
        ballActor.setPosition((VIEWPORT_WIDTH) / 2 / PIXEL_TO_METER, (VIEWPORT_WIDTH * ratio) / 2 / PIXEL_TO_METER);
        addActor(ballActor);

        AlphaAction alphaAction = new AlphaAction();
        alphaAction.setAlpha(.5f);
        alphaAction.setDuration(10);
        ballActor.addAction(alphaAction);

        GroundActor groundActor = new GroundActor(game);
        groundActor.setPosition(0, 0);
        addActor(groundActor);

        world = new World(new Vector2(0, -3), true);
        ballBody = ballActor.createBody(world);
        groundActor.createBody(world);

        // Touch events
        ballActor.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //ballBody.applyForceToCenter(0, 2, true);
                return true;
            }
        });

        // Fling events
        ballActor.addListener(new ActorGestureListener(){
            @Override
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                Vector2 vector = new Vector2(velocityX / 1000, velocityY / 1000);
                vector.rotateRad(ballBody.getAngle());
                ballBody.applyForceToCenter(vector, true);
                kickSound.play();
            }
        });

        kickSound = game.getAssetManager().get("kick.wav");

        Music music = game.getAssetManager().get("music.mp3");
        music.setLooping(true);
        music.play();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Step the world
        world.step(delta, 6, 2);

        // Update the ball actor position
        ballActor.setRotation((float) Math.toDegrees(ballBody.getAngle()));
        ballActor.setPosition(ballBody.getPosition().x / PIXEL_TO_METER, ballBody.getPosition().y / PIXEL_TO_METER);
    }
}
