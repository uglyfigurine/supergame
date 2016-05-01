package com.msbhurji.supergame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.msbhurji.supergame.SuperGame;
import com.msbhurji.supergame.objects.Dot;

import java.util.Random;

public class MainGame implements Screen {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private TextureAtlas atlas;

    private Color[] dotColors = new Color[]{Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.BLUE};
    private Array<Dot> pellets;
    private Viewport viewport;
    private Dot redDot, marker;

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        atlas = new TextureAtlas("sprites.txt");

        redDot = new Dot(atlas, Color.RED, 30, 0, 0);

        marker = new Dot(atlas, Color.SLATE, 100, 0, 0);
        marker.setPosition((Gdx.graphics.getWidth() - marker.getRadius())/2 , (Gdx.graphics.getHeight() - marker.getRadius())/2);

        pellets = new Array<Dot>(100);
        for (int i = 0; i <= 100; i++) {
            int colorIdx = new Random().nextInt(dotColors.length);
            int maxX = Gdx.graphics.getWidth();
            int maxY = Gdx.graphics.getHeight();
            final Dot pellet = new Dot(atlas, dotColors[colorIdx], 10, new Random().nextInt(maxX), new Random().nextInt(maxY));
            pellets.add(pellet);
        }

        float viewportScale = 0.5f;
        viewport = new FitViewport(Gdx.graphics.getWidth() * viewportScale,
                Gdx.graphics.getHeight() * viewportScale);

    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        atlas.dispose();
        pellets.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float camX, camY;
        if (redDot.x() > viewport.getCamera().viewportWidth/2) {
            camX = redDot.x();
            if (Gdx.graphics.getWidth() - redDot.x() < viewport.getCamera().viewportWidth/2) {
                camX = Gdx.graphics.getWidth() - viewport.getCamera().viewportWidth/2;
            }
        } else {
            camX = viewport.getCamera().viewportWidth/2;
        }
        if (redDot.y() > viewport.getCamera().viewportHeight/2) {
            camY = redDot.y();
            if (Gdx.graphics.getHeight() - redDot.y() < viewport.getCamera().viewportHeight/2) {
                camY = Gdx.graphics.getHeight() - viewport.getCamera().viewportHeight/2;
            }
        } else {
            camY = viewport.getCamera().viewportHeight/2;
        }

        viewport.getCamera().position.set(camX, camY, 0);
        viewport.getCamera().update();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        for (int i=0; i < pellets.size; i++) {
            Dot pellet = pellets.get(i);
            Circle redDotBoundingShape = redDot.getBoundingShape();
            if (redDotBoundingShape.contains(pellet.getBoundingShape())) {
                pellets.removeIndex(i);
                redDot.eat(pellet);
                continue;
            }
            pellet.draw(batch);
        }
        marker.draw(batch);
        redDot.draw(batch);
        batch.end();

//        Enable this to see the shapes backing a sprite
        if (SuperGame.DEBUG) {
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.begin();
            for (int i = 0; i < pellets.size; i++) {
                Dot pellet = pellets.get(i);
                pellet.draw(shapeRenderer);
            }
            marker.draw(shapeRenderer);
            redDot.draw(shapeRenderer);
            shapeRenderer.end();
        }
        // add logic to handle case where the dot becomes too big
        handleInput(delta);
    }

    private void handleInput(float delta) {
        final float x = redDot.x();
        final float y = redDot.y();
        float speed = 100, newX = x, newY = y;
        if (Gdx.input.isKeyPressed(Input.Keys.W)
                || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newY = y + speed*delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)
                || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newX = x - speed*delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)
                || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newY = y - speed*delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)
                || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newX = x + speed*delta;
        }

        if (newX < 0) {
            newX = 0;
        }
        if (newX > Gdx.graphics.getWidth() - redDot.getRadius()*2) {
            newX = Gdx.graphics.getWidth() - redDot.getRadius()*2;
        }
        if (newY < 0) {
            newY = 0;
        }
        if (newY > Gdx.graphics.getHeight() - redDot.getRadius()*2) {
            newY = Gdx.graphics.getHeight() - redDot.getRadius()*2;
        }
        redDot.setPosition(newX, newY);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.getCamera().position.set(viewport.getScreenWidth()/2f, viewport.getScreenHeight()/2f, 0);
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
