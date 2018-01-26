package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import static com.henryandlincoln.swimmyfishy.Fish.STATE.*;


public class Fish implements GameObject {

    enum STATE {

        FLAP,
        ABILITY
    }

    private static float VELOCITY = 0.1f;
    private static float GRAVITY = 0.5f;

    private int x;
    private int y;
    private final int SCREEN_HEIGHT;
    private final int SCREEN_WIDTH;
    private final int spriteWidth;
    private final int spriteHeight;
    private static final int SPRITE_SHEET_ROWS = 1;
    private static final int SPRITE_SHEET_COLS = 2;
    private float distance;
    private float angle;
    private STATE state;
    private Matrix matrix;
    private final Animation flapAnimation;
    private final AnimationManager animationManager;


    public Fish(Bitmap spriteSheet, int x, int y, int SCREEN_WIDTH, int SCREEN_HEIGHT) {

        /* Create new matrix that rotates fish when it is falling */
        matrix = new Matrix();


        spriteWidth = spriteSheet.getWidth()/SPRITE_SHEET_COLS;
        spriteHeight = spriteSheet.getHeight()/SPRITE_SHEET_ROWS;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.x = x;
        this.y = y;


        /* Set up flap animation and pass into animation manager which will play the animation 5 times per second */
        Bitmap[] flapAnim = new Bitmap[SPRITE_SHEET_COLS];
        for (int i =0;i<SPRITE_SHEET_COLS;i++) {
            flapAnim[i] = Bitmap.createBitmap(spriteSheet, (i* spriteWidth), 0, spriteWidth, spriteHeight);
        }
        this.flapAnimation = new Animation(flapAnim,0.2f);
        animationManager = new AnimationManager(flapAnimation);

    }

    @Override
    public void update() {

        VELOCITY += GRAVITY;
        distance = VELOCITY * 3.0f;

        this.y +=  (int) distance;

        angle = VELOCITY * 5.0f;

        /* Keep object at edge of screen if there */
        if (this.y <= 0 )  {
            this.y = 0;
        }
        else if (this.y >= SCREEN_HEIGHT*5/6 - spriteHeight){
            this.y = (SCREEN_HEIGHT*5/6 - spriteHeight);
            //angle = 90;
        }

        this.rotate(angle);

        animationManager.update();
        animationManager.playAction(0);

    }

    @Override
    public int getX() {

        return this.x;
    }

    @Override
    public void draw(Canvas canvas){

        animationManager.draw(canvas,matrix);

    }

    private void rotate(float angle){

        angle = (angle>= 90) ? 90 : angle;

        this.matrix.reset();
        this.matrix.setRotate(angle,spriteWidth/2,spriteHeight/2);
        this.matrix.postTranslate(100,y);

    }


    public void jump()  {

        state = FLAP;
        VELOCITY = - 6.0f;
    }

    public void resetAngle(){
        this.angle = 0;
        this.matrix.reset();
        this.matrix.setRotate(0,spriteWidth/2,spriteHeight/2);
        this.matrix.postTranslate(100,y);
    }
}




