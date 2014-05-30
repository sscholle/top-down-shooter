package com.badlogic.gradletest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 2014/05/30
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Ground {
    Texture tex;
    Sprite ground;
    Sprite ground2;
    float _width;
    float _height;
    float _xVelocity;
    float _yVelocity;

    public Ground(String groundTexturePath, float appWidth, float appHeight, float initalXVelocity){
        _width = appWidth;
        _height = appHeight;
        _yVelocity = initalXVelocity;
        tex = new Texture(groundTexturePath);
        ground = new Sprite(tex);
        ground.setSize(_width, _height);

        ground2 = new Sprite(tex);
        ground2.setSize(_width, _height);
        ground2.setPosition(0.0f, ground.getHeight()+ground.getY());
    }

    public void render(SpriteBatch batch)
    {
        float deltaYV = -(Gdx.graphics.getDeltaTime() * _yVelocity);
        if (ground.getY() <= -_height){
            // off screen - move back up
            ground.setPosition(0, ground2.getY()+ground2.getHeight());
        }
        if (ground2.getY() <= -_height){
            // off screen - move back up
            ground2.setPosition(0, ground.getY()+ground.getHeight());
        }
        ground.translateY(Math.round(deltaYV));
        ground2.translateY(Math.round(deltaYV));
        //System.out.println("g1 pos: x-"+(int)(ground.getX())+" y:"+(int)(ground.getY()));
        //System.out.println("g2 pos: x-"+(int)(ground2.getX())+" y:"+(int)(ground2.getY()));
        ground.draw(batch);
        ground2.draw(batch);
       // elapsedTime = Gdx.graphics.getDeltaTime() * _yVelocity;
    }

    public void dispose(){
        tex.dispose();
    }
}
