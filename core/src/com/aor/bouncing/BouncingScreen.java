package com.aor.bouncing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * The only game screen.
 */
public class BouncingScreen extends ScreenAdapter {
    /**
     * The game.
     */
    private final BouncingBalls game;

    /**
     * The ball texture.
     */
    private final Texture ballTexture;

    /**
     * Creates a new screen loadings all needed textures.
     *
     * @param game the game
     */
    public BouncingScreen(BouncingBalls game) {
        this.game = game;

        game.getAssetManager().load("ball.png", Texture.class);
        game.getAssetManager().finishLoading();

        ballTexture = (Texture) game.getAssetManager().get("ball.png");
    }

    /**
     * Renders the screen
     *
     * @param delta time since last rendered in seconds
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        // Clear the screen
        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        // Draw the texture
        game.getBatch().begin();
        game.getBatch().draw(ballTexture, 100, 100);
        game.getBatch().end();
    }
}
