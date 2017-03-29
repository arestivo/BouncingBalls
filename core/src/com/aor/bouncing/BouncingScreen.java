package com.aor.bouncing;

import com.badlogic.gdx.ScreenAdapter;
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

        game.getBatch().begin();
        game.getBatch().draw(ballTexture, 100, 100);
        game.getBatch().end();
    }
}
