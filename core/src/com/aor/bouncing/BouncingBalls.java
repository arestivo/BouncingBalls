package com.aor.bouncing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Simple bouncing ball "game" example.
 */
public class BouncingBalls extends Game {
    /**
     * Manages the game assets
     */
    private AssetManager assetManager;

    /**
     * The sprite batch used for drawing to the screen
     */
    private SpriteBatch batch;

    /**
     * Creates a new game and set the current screen
     */
    @Override
    public void create() {
        assetManager = new AssetManager();
        batch = new SpriteBatch();

        setScreen(new BouncingScreen(this));
    }

    /**
     * Returns the asset manager
     *
     * @return the asset manager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Returns the sprite batch
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch() {
        return batch;
    }
}
