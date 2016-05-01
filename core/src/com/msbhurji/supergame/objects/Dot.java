package com.msbhurji.supergame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Dot {
    private float radius;
    private Sprite sprite;
    private Circle boundingCircle;

    public Dot(TextureAtlas atlas, Color color, float radius, float x, float y) {
        this.radius = radius;
        Sprite whiteCircle = atlas.createSprite("white-circle-a100");
        sprite = new Sprite(whiteCircle);
        sprite.setColor(color);
        boundingCircle = new Circle(0, 0, radius);
        setRadius(radius);
        setPosition(x, y);
    }

    public Circle getBoundingShape() {
        return boundingCircle;
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
        boundingCircle.setPosition(x + radius, y + radius);
    }

    public float getRadius() {
        return radius;
    }

    private void setRadius(float radius) {
        this.radius = radius;
        sprite.setSize(radius*2, radius*2);
        boundingCircle.setRadius(radius);
    }

    public float x() {
        return sprite.getX();
    }

    public float y() {
        return sprite.getY();
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    // debug method
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(boundingCircle.x, boundingCircle.y, radius);
    }

    public void eat(Dot dot) {
        float mulFactor = dot.radius/radius > 0.1 ? 0.2f : 0;
        setRadius(radius + dot.radius * mulFactor);
    }
}
