package com.aor.bouncing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * The game stage
 */

class ScoreStage extends Stage {
    /**
     * Viewport width in meters. Height depends on screen ratio
     */
    static final int VIEWPORT_WIDTH = 400;

    /**
     * The score label
     */
    private final Label scoreLabel;

    ScoreStage(BouncingBalls game) {
        // Set the viewport
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        setViewport(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * ratio));

        scoreLabel = new Label("0", new Label.LabelStyle(new BitmapFont(), null));
        scoreLabel.setPosition(10, VIEWPORT_WIDTH * ratio - 30);
        scoreLabel.setColor(Color.WHITE);
        addActor(scoreLabel);
    }


    public void setScore(int score) {
        scoreLabel.setText("" + score);
    }
}
