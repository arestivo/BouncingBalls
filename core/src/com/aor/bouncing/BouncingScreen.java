package com.aor.bouncing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * The only game screen.
 */
class BouncingScreen extends ScreenAdapter {
    private final GameStage gameStage;
    private final ScoreStage scoreStage;

    /**
     *
     * @param game the game
     */
    BouncingScreen(BouncingBalls game) {
        this.gameStage = new GameStage(game);
        this.scoreStage = new ScoreStage(game);

        // Sets the stage as its input processor
        Gdx.input.setInputProcessor(gameStage);
    }

    /**
     * Renders the screen
     *
     * @param delta time since last rendered in seconds
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        // Steps the stage
        gameStage.act(delta);
        scoreStage.setScore((int)gameStage.getMaximumHeight());

        // Draws the stage
        gameStage.draw();
        scoreStage.draw();
    }

    /**
     * Resize the stage viewport when the screen is resized
     *
     * @param width the new screen width
     * @param height the new screen height
     */
    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height, true);
        scoreStage.getViewport().update(width, height, true);
    }
}
