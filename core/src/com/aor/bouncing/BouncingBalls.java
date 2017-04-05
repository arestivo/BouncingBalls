package com.aor.bouncing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Simple bouncing ball "game" example.
 */
public class BouncingBalls extends Game {
    /**
     * Manages the game assets
     */
    private AssetManager assetManager;

    /**
     * Creates a new game and set the current screen
     */
    @Override
    public void create() {
        assetManager = new AssetManager();

        // Sets the game screen
        setScreen(new BouncingScreen(this));
    }

    /**
     * Returns the asset manager
     *
     * @return the asset manager
     */
    AssetManager getAssetManager() {
        return assetManager;
    }
}
