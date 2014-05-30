package com.badlogic.gradletest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 2014/05/30
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bullet {
    Texture tex;
    Sprite sprite;
    float _x;
    float _y;
    float _a;
    float _v;

    public Bullet(String texturePath, float scale, float xPos, float yPos, float angle, float velocity)
    {

    }

    public void dispose(){
        tex.dispose();
    }
    public void render(SpriteBatch batch)
    {

    }
}
