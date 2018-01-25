package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import static com.henryandlincoln.swimmyfishy.Fish.STATE.FLAP;


public class Fish implements GameObject {

    enum STATE {
            FLAP,
        ABILITY
    }

    private static float VELOCITY = 0.1f;
    private static float GRAVITY = 0.5f;

    private static final int SPRITE_SHEET_ROWS = 1;
    private static final int SPRITE_SHEET_COLS = 2;
    private final int SCREEN_HEIGHT;
    private final int SCREEN_WIDTH;
    private boolean falling;
    private float distance;
    private float angle;
    private STATE state;
    private Matrix matrix;
    private int x;
    private int y;
    private Animation flapAnimation;
    private AnimationManager animationManager;
    private int spriteWidth;
    private int spriteHeight;

    public Fish(Bitmap spriteSheet, int x, int y, int SCREEN_WIDTH, int SCREEN_HEIGHT) {

        matrix = new Matrix();
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.x = x;
        this.y = y;

        int totalWidth = spriteSheet.getWidth();
        int totalHeight = spriteSheet.getHeight();
        spriteWidth = totalWidth/SPRITE_SHEET_COLS;
        spriteHeight = totalHeight/SPRITE_SHEET_ROWS;

        /* Set up the flap animation */
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
        return 0;
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
}




