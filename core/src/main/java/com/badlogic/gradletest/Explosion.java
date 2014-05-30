package com.badlogic.gradletest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 2014/05/29
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Explosion {

    Texture exp;
    Animation animation;
    float _x;
    float _y;
    float elapsedTime = 0.0f;

    public Explosion(String expTexPath, float xPos, float yPos)
    {
        exp = new Texture(Gdx.files.internal(expTexPath));
        _x = xPos;
        _y = yPos;
        // TODO: process this once in the manager
        TextureRegion[][] tmp = TextureRegion.split(exp, exp.getWidth() / 5, exp.getHeight() / 5);
        TextureRegion[] walkFrames = new TextureRegion[5 * 5];
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(1/15f, walkFrames);
        animation.setPlayMode(Animation.NORMAL);
    }

    public void dispose()
    {
        exp.dispose();
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(animation.getKeyFrame(elapsedTime, false), _x, _y);
        elapsedTime += Gdx.graphics.getDeltaTime();
    }
}
