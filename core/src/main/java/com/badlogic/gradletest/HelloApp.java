
package com.badlogic.gradletest;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.Input.*;
import java.util.ArrayList;
import java.util.Vector;


public class HelloApp extends ApplicationAdapter implements ApplicationListener, InputProcessor {
	// TODO: UI Class
    SpriteBatch batch;
	Texture titleImg;
    BitmapFont font;
    Sprite bgSprite;

    // TODO: Plane Class
    Texture subjectTextureLeft;
    Sprite planeLeft;
    Texture subjectTextureRight;
    Sprite planeRight;
    Texture subjectTexture;
    Sprite planeNormal;
    Sprite subjectSprite;
    Texture planeShadowTexture;
    Sprite planeShadow;
    Vector<Texture> propTexArray;
    Texture propellerTexture1;
    Texture propellerTexture2;
    Sprite propeller;
    Integer propellerNumber;

    float planeScale = 0.5f;

    // TODO: Game Model
    Integer appWidth;
    Integer appHeight;
    String playerState;
    Integer score;
    float lastTimeScore;


    // TODO: Movement Class
    double currentMaxAccelX;
    double currentMaxAccelY;

    boolean directionKeyReleased;
    float elapsedTime;
    float lastInputCheck;

    float baseAcell;
    float highestAccelX;
    float highestAccelY;

    ArrayList<Enemy> enemyCollection = new ArrayList<Enemy>();// = new Sprite();
    Integer enemyCollectionSize;


    ArrayList<Explosion> expCollection = new ArrayList<Explosion>();// = new Sprite();
    Integer expCollectionSize;

    Ground ground;

    // SOUNDS:
    Sound pigeon;
    Enemy tmp;
    Camera camera;

    @Override
	public void create () {
        appWidth = Gdx.graphics.getWidth();
        appHeight = Gdx.graphics.getHeight();

        // viewport
        camera = new OrthographicCamera(appWidth, appHeight);
        camera.update();

        lastTimeScore = 0.0f;
        score = 0;

        enemyCollectionSize = 0;
        expCollectionSize = 0;

        batch = new SpriteBatch();

        pigeon = Gdx.audio.newSound(Gdx.files.internal("pigeon.mp3"));

        ground = new Ground("airplane/airPlanesBackground.png", appWidth, appHeight, 80.0f);

        font = new BitmapFont();

        font.setColor(Color.WHITE);
        playerState = PlayerState.NORMAL;

        // control management
        directionKeyReleased = true;

        // player management
        baseAcell = 0.333f;
        highestAccelX = 6.66f;
        highestAccelY = 6.66f;

        subjectTextureLeft = new Texture(Gdx.files.internal("airplane/PLANE_8_L.png"));
        planeLeft = new Sprite(subjectTextureLeft);
        subjectTextureRight = new Texture(Gdx.files.internal("airplane/PLANE_8_R.png"));
        planeRight = new Sprite(subjectTextureRight);
        subjectTexture = new Texture(Gdx.files.internal("airplane/PLANE_8_N.png"));
        planeNormal = new Sprite(subjectTexture);
        subjectSprite = new Sprite(subjectTexture);
        subjectSprite.setSize(planeScale*subjectTexture.getWidth(), planeScale*subjectTexture.getHeight());

        //subjectSprite.setSize(planeNormal.getWidth(), planeNormal.getHeight());
        subjectSprite.setOrigin(planeNormal.getWidth()/2, 0);

        planeShadowTexture = new Texture(Gdx.files.internal("airplane/PLANE_8_SHADOW.png"));
        planeShadow = new Sprite(planeShadowTexture);
        planeShadow.setScale(0.3f, 0.3f);

        propellerNumber = 1;
        propellerTexture1 = new Texture(Gdx.files.internal("airplane/PLANE_PROPELLER_1.png"));
        propellerTexture2 = new Texture(Gdx.files.internal("airplane/PLANE_PROPELLER_2.png"));
        propTexArray = new Vector<Texture>();
        propTexArray.add(0, propellerTexture1);
        propTexArray.add(1, propellerTexture2);
        propeller = new Sprite(propellerTexture1);
        propeller.setScale(planeScale);

        currentMaxAccelX = 0;
        currentMaxAccelY = 0;

        initPlane();
        scheduleEnemies();

//		try {
//			new FreeTypeFontGenerator(Gdx.files.internal("test.fnt"));
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		Bullet.init();

        Gdx.input.setInputProcessor(this);
        elapsedTime = 0;
        lastInputCheck = 0;
	}

    private void initPlane(){
        Timer.schedule(new Task() {
            @Override
            public void run() {
                if (propellerNumber == 2)
                    propellerNumber = 1;
                else
                    propellerNumber++;

                propeller.setTexture(propTexArray.get(propellerNumber-1));
            }
        }, 0, 1 / 30.0f);
    }

    private void scheduleEnemies(){
        Timer.schedule(new Task() {
            @Override
            public void run() {
                EnemiesAndClouds();
            }
        }, 0, 1.0f);
    }

    private void EnemiesAndClouds()
    {
        Integer GoOrNot = Math.round((float)Math.random());
        System.out.println("GoOrNot: "+GoOrNot);
        if(GoOrNot == 1){
            Enemy enemy;

            //int randomEnemy = Util.getRandomNumberBetween(0, 1);
            double rand = Math.random();
            if(rand < 0.5f)
                enemy = new Enemy("airplane/PLANE_1_N.png", planeScale, appWidth/2, appHeight/2);
            else
                enemy = new Enemy("airplane/PLANE_2_N.png", planeScale, appWidth/2, appHeight/2);

            enemy.initPath(appWidth, appHeight);
            enemyCollection.add(enemy);
            enemyCollectionSize++;
        }
    }

    // todo: more disposal
    public void dispose() {
        batch.dispose();
        titleImg.dispose();
        font.dispose();
        subjectTexture.dispose();
    }

	@Override
	public void render () {

        // ACELLEROMETER CONTROLS
        // TODO: Controls System
        if(Gdx.app.getType() == Application.ApplicationType.iOS || Gdx.app.getType() == Application.ApplicationType.Android)
        {
            int deviceAngle = Gdx.input.getRotation();
            Orientation orientation = Gdx.input.getNativeOrientation();
            float accelX = Gdx.input.getAccelerometerX()*-1;
            if(accelX > highestAccelX)
                currentMaxAccelX = highestAccelX;
            else
                currentMaxAccelX = accelX;

            float accelY = Gdx.input.getAccelerometerY()*-1;
            if(accelY > highestAccelY)
                currentMaxAccelY = highestAccelY;
            else
                currentMaxAccelY = accelY;
        }

		Gdx.gl.glClearColor(0.5f, 1, 0.5f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
        ground.render(batch);
        timeScore();
        font.draw(batch, "Score: "+score, 20, appHeight - 20);

        font.draw(batch, "Planes: "+enemyCollectionSize, 20, appHeight - 50);

        font.draw(batch, "Explosions: "+expCollectionSize, 20, appHeight - 80);
        positionPlane();
        checkInput();
        planeShadow.draw(batch);
        subjectSprite.draw(batch);
        propeller.draw(batch);

        for (int x = 0; x<enemyCollectionSize; x++)
        {
            enemyCollection.get(x).render(batch);
            if(enemyCollection.get(x).checkOverlap(subjectSprite.getBoundingRectangle()))
            {
                // sound
                pigeon.play();
                tmp = enemyCollection.get(x);
                tmp.setDispose();
                Explosion exp = new Explosion("explosion19.png", (tmp._x + tmp.sprite.getWidth()/2), tmp._y + tmp.sprite.getHeight()/2);
                expCollection.add(exp);
                expCollectionSize++;
                score += 250000;
            }
        }

        for (int x = 0; x<expCollectionSize; x++)
        {
            expCollection.get(x).render(batch);
        }

        batch.end();

        // GC after rendering


        for (int x = 0; x<enemyCollectionSize; x++)
        {
            if(enemyCollection.get(x)._dispose)
            {
                enemyCollection.get(x).dispose();
                // use explosion manager to spawn new explosion
                enemyCollection.remove(x);
                enemyCollectionSize--;
            }
        }

        for (int x = 0; x<expCollectionSize; x++)
        {
            if (expCollection.get(x).elapsedTime > 1.6f){
                expCollection.get(x).dispose();
                // use explosion manager to spawn new explosion
                expCollection.remove(x);
                expCollectionSize--;
            }
        }

        Util.cleanNulls(enemyCollection);
        Util.cleanNulls(expCollection);
	}

    public void timeScore()
    {
        if ((lastTimeScore + 1.0f) < (elapsedTime))
        {
            score += 100;
            lastTimeScore = elapsedTime;
        }
    }

    public void checkInput()
    {
        //System.out.println("elapsedTime: "+elapsedTime);
        //System.out.println("lastInputCheck: "+lastInputCheck);
        //System.out.println("currentMaxAccelX: "+currentMaxAccelX);
        //System.out.println("currentMaxAccelY: "+currentMaxAccelY);
        if ((lastInputCheck + 0.1f) < (elapsedTime))
        {
            if(Gdx.input.isKeyPressed(Keys.LEFT)){

                if(currentMaxAccelX > 0)
                    currentMaxAccelX = 0;
                currentMaxAccelX-=baseAcell;
            }
            if(Gdx.input.isKeyPressed(Keys.RIGHT)){

                if(currentMaxAccelX < 0)
                    currentMaxAccelX = 0;
                currentMaxAccelX+=baseAcell;
            }
            if(Gdx.input.isKeyPressed(Keys.DOWN)){

                if(currentMaxAccelY > 0)
                    currentMaxAccelY = 0;
                currentMaxAccelY-=baseAcell;
            }
            if(Gdx.input.isKeyPressed(Keys.UP)){

                if(currentMaxAccelY < 0)
                    currentMaxAccelY = 0;
                currentMaxAccelY+=baseAcell;
            }
            lastInputCheck = elapsedTime;
        }
        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    public void positionPlane()
    {
        float maxY = appWidth - subjectSprite.getWidth();
        float minY = 0;

        float maxX = appHeight - subjectSprite.getHeight();
        float minX = 0;

        float newY = 0;
        float newX = 0;

        if(currentMaxAccelX > baseAcell){
            newX = (float)currentMaxAccelX * 10;
            subjectSprite.setTexture(subjectTextureRight);
            //subjectSprite.set(planeRight);
        }
        else if(currentMaxAccelX < -baseAcell){
            newX = (float)currentMaxAccelX * 10;
            subjectSprite.setTexture(subjectTextureLeft);
            //subjectSprite.set(planeLeft);
        }
        else{
            newX = (float)currentMaxAccelX * 10;
            subjectSprite.setTexture(subjectTexture);
            //subjectSprite.set(planeNormal);
        }

        newY = 0.0f + (float)currentMaxAccelY *10;
        // TODO: center shadow origin
        float newXshadow = newX+subjectSprite.getX();
        float newYshadow = newY+subjectSprite.getY();

        newXshadow = Math.min(Math.max(newXshadow, minY + 15), maxY + 15) / 1.3f;
        newYshadow = Math.min(Math.max(newYshadow, minX - 15), maxX - 15) / 1.3f;

        float newXpropeller = newX+propeller.getX();
        float newYpropeller = newY+propeller.getY();

        //newXpropeller = Math.min(Math.max((subjectSprite.getWidth()/2),minY),maxY);
        //newYpropeller = Math.min(Math.max(newYpropeller,minX+(subjectSprite.getHeight())-15),maxX+(subjectSprite.getHeight()/2)-5);


        newX = Math.min(Math.max(newX + subjectSprite.getX(), minY), maxY);
        newY = Math.min(Math.max(newY + subjectSprite.getY(), minX), maxX);

        newXpropeller = newX + (subjectSprite.getWidth()/2) - (propeller.getWidth()/2);
        newYpropeller = newY + (subjectSprite.getHeight())-(15*planeScale);

        subjectSprite.setPosition(Math.round(newX), Math.round(newY));
        planeShadow.setPosition(newXshadow, newYshadow);
        propeller.setPosition(newXpropeller, newYpropeller);
    }

    public void fireBullet()
    {
        Sprite _bullet = new Sprite();
        _bullet.setColor(Color.BLUE);
        _bullet.setSize(10, 10);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        initPlane();
    }

    @Override
    public boolean keyDown(int keycode) {
//        float moveAmount = 0.3f;
//        if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
//            moveAmount = 10.0f;
//
//        if(keycode == Keys.LEFT)
//            currentMaxAccelX-=moveAmount;
//        if(keycode == Keys.RIGHT)
//            currentMaxAccelX+=moveAmount;
        if(keycode == Keys.SPACE)
            fireBullet();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode == Keys.LEFT)
            directionKeyReleased = true ;
        if(keycode == Keys.RIGHT)
            directionKeyReleased = true;
        return true;
        //return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyTyped(char character) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scrolled(int amount) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
