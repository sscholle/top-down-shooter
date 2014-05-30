package com.badlogic.gradletest;

import android.graphics.Point;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 2014/05/29
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class Enemy {
    Texture texture;
    Sprite sprite;
    float speed = 0.15f;
    float current = 0;
    Vector2 out = new Vector2();
    Vector2 tmp = new Vector2();
    Vector2[] dataSet = new Vector2[4];
    CatmullRomSpline<Vector2> myCatmull;

    float _x;
    float _y;
    boolean _dispose = false;

    public Enemy(String texturePath, float scale, int xPos, int yPos){
        texture = new Texture(Gdx.files.internal(texturePath));
        sprite = new Sprite(texture);

        sprite.setSize(scale*texture.getWidth(), scale*texture.getHeight());
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setPosition(xPos, yPos);
        //System.out.println("scale: "+scale);
        //sprite.setScale(scale);
        
        // then init path ()
    }

    public void setDispose()
    {
        _dispose = true;
    }

    public void dispose()
    {
        // empty
        texture.dispose();
    }

    public void initPath(int appWidth, int appHeight){

        //random values
        float xStart = Util.getRandomNumberBetween(0+sprite.getWidth(), appWidth-sprite.getWidth());
        float xEnd =  Util.getRandomNumberBetween(0+sprite.getWidth(), appWidth-sprite.getWidth());

        //ControlPoint1
        float cp1X =  Util.getRandomNumberBetween(0 + sprite.getWidth(), appWidth - sprite.getWidth());
        float cp1Y =  Util.getRandomNumberBetween(0+sprite.getWidth(), appWidth-sprite.getHeight());

        //ControlPoint2
        float cp2X =  Util.getRandomNumberBetween(0+sprite.getWidth(), appWidth-sprite.getWidth());
        float cp2Y =  Util.getRandomNumberBetween(0, cp1Y);

        Vector2 s = new Vector2(xStart, 1200);
        Vector2 e = new Vector2(xEnd, -sprite.getHeight());
        Vector2 cp1 = new Vector2(cp1X, cp1Y);
        Vector2 cp2 = new Vector2(cp2X, cp2Y);

        dataSet[0] = s;
        dataSet[1] = cp2;
        dataSet[2] = cp1;
        dataSet[3] = e;

        // enemy path
        myCatmull = new CatmullRomSpline<Vector2>(dataSet, true);
    }
    
    public void render(SpriteBatch batch)
    {
        current += Gdx.graphics.getDeltaTime() * speed;
        if(current >= 1)
            current -= 1;
        myCatmull.valueAt(out, current);
        sprite.setRotation((float)calcRotationAngleInDegrees(_x, _y, out.x, out.y)-180);
        _x = out.x;
        _y = out.y;
        sprite.setPosition(_x, _y);
        sprite.draw(batch);
    }

    public static double calcRotationAngleInDegrees(float cX, float cY, float tX, float tY)
    {
        // calculate the angle theta from the deltaY and deltaX values
        // (atan2 returns radians values from [-PI,PI])
        // 0 currently points EAST.
        // NOTE: By preserving Y and X param order to atan2,  we are expecting
        // a CLOCKWISE angle direction.
        double theta = Math.atan2(tY - cY, tX - cX);

        // rotate the theta angle clockwise by 90 degrees
        // (this makes 0 point NORTH)
        // NOTE: adding to an angle rotates it clockwise.
        // subtracting would rotate it counter-clockwise
        theta += Math.PI/2.0;

        // convert from radians to degrees
        // this will give you an angle from [0->270],[-180,0]
        double angle = Math.toDegrees(theta);

        // convert to positive range [0-360)
        // since we want to prevent negative angles, adjust them now.
        // we can assume that atan2 will not return a negative value
        // greater than one partial rotation
        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public boolean checkOverlap(Rectangle rectangle)
    {
        return !(_x > rectangle.x + rectangle.width || _x + sprite.getWidth() < rectangle.x || _y > rectangle.y + rectangle.height || _y + sprite.getHeight() < rectangle.y);
    }

    public void explode()
    {
        // dispose this
    }
}
