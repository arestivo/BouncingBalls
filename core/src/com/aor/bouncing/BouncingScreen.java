package com.aor.bouncing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The only game screen.
 */
public class BouncingScreen extends ScreenAdapter {
    /**
     * Viewport width in meters. Height depends on screen ratio
     */
    private static final int VIEWPORT_WIDTH = 6;

    /**
     * A football is 22cm in diameter and the sprite has a width of 200px
     */
    private static final float PIXEL_TO_METER = 0.22f / 200;

    /**
     * The game.
     */
    private final BouncingBalls game;

    /**
     * The ball texture.
     */
    private final Texture ballTexture;

    /**
     * The ground texture.
     */
    private final Texture groundTexture;

    /**
     * The camera.
     */
    private final OrthographicCamera camera;

    /**
     * The physical world.
     */
    private final World world;

    /**
     * The ball physical body
     */
    private final Body ballBody;

    /**
     * The ground body
     */
    private final Body groundBody;

    /**
     * Physics debug camera
     */
    private final Box2DDebugRenderer debugRenderer;

    /**
     * Physics debug matrix transformation (meters to pixels)
     */
    private Matrix4 debugCamera;

    /**
     * Creates a new screen loadings all needed textures.
     *
     * @param game the game
     */
    public BouncingScreen(BouncingBalls game) {
        this.game = game;

        // Load the textures
        game.getAssetManager().load("ball.png", Texture.class);
        game.getAssetManager().load("ground.png", Texture.class);
        game.getAssetManager().finishLoading();

        // Get the textures
        ballTexture = game.getAssetManager().get("ball.png");
        groundTexture = game.getAssetManager().get("ground.png");

        groundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        // Create the camera
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio);
        camera.position.set(new Vector3(camera.viewportWidth / 2, camera.viewportHeight / 2, 0)); // middle of the viewport

        // Create debug camera
        debugRenderer = new Box2DDebugRenderer();
        debugCamera = camera.combined.cpy();
        debugCamera.scl(1 / PIXEL_TO_METER);

        // Create the physical world
        world = new World(new Vector2(0, -10), true);

        ballBody = createBallBody();
        groundBody = createGroundBody();
    }

    private Body createGroundBody() {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Create the ball body
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        Body body = world.createBody(bodyDef);
        body.setTransform(0, 0, 0); // Bottom left corner

        // Create rectangular shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(VIEWPORT_WIDTH, 0.5f); // Viewport width and 50cm height

        // Create ground fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = .5f;      // how heavy is the ball
        fixtureDef.friction =  .5f;    // how slippery is the ball
        fixtureDef.restitution =  .5f; // how bouncy is the ball

        // Attach ficture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        rectangle.dispose();

        return body;
    }

    /**
     * Creates the ball body.
     */
    private Body createBallBody() {
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

        // Attach ficture to body
        body.createFixture(fixtureDef);

        // Dispose of circle shape
        circle.dispose();

        return body;
    }

    /**
     * Renders the screen
     *
     * @param delta time since last rendered in seconds
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        // Update the world
        world.step(delta, 6, 2);

        // Update the camera
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        // Clear the screen
        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        // Draw the texture
        game.getBatch().begin();
        game.getBatch().draw(ballTexture, (ballBody.getPosition().x -.11f) / PIXEL_TO_METER,  (ballBody.getPosition().y - .11f) / PIXEL_TO_METER);
        game.getBatch().draw(groundTexture, 0,  0, VIEWPORT_WIDTH / PIXEL_TO_METER, .50f / PIXEL_TO_METER);
        game.getBatch().end();

        // Render debug camera
        debugCamera = camera.combined.cpy();
        debugCamera.scl(1 / PIXEL_TO_METER);
        debugRenderer.render(world, debugCamera);
    }
}
