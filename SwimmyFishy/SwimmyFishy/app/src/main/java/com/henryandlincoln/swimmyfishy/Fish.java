package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import static com.henryandlincoln.swimmyfishy.Fish.STATE.*;


public class Fish implements GameObject {

    enum STATE {

        FLAP,
        ALIVE,
        DEAD
    }

    private static float VELOCITY = 0.1f;
    private static float GRAVITY = 0.5f;

    private int x;
    private int y;

    private final int SCREEN_HEIGHT;
    private final int SCREEN_WIDTH;
    private final int spriteWidth;
    private final int spriteHeight;
    private final float SCALE;

    private static final int SPRITE_SHEET_ROWS = 1;
    private static final int SPRITE_SHEET_COLS = 2;

    private float distance;
    private float angle;
    private Matrix matrix;

    private STATE state = ALIVE;

    private final Animation flapAnimation;
    private final AnimationManager animationManager;

    private Paint paint;


    public Fish(Bitmap spriteSheet, int x, int y, int SCREEN_WIDTH, int SCREEN_HEIGHT) {

        /* Create new matrix that rotates fish when it is falling */
        matrix = new Matrix();

        /* Set up the sprite sheet for extracting animations */
        spriteWidth = spriteSheet.getWidth()/SPRITE_SHEET_COLS;
        spriteHeight = spriteSheet.getHeight()/SPRITE_SHEET_ROWS;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.x = x;
        this.y = y;

        /* Scale all values pertaining to fish movement */
        this.SCALE = SCREEN_HEIGHT/1920.f;
        this.GRAVITY *= SCALE;
        this.VELOCITY *=SCALE;

        /* Set up flap animation and pass into animation manager which will play the animation 5 times per second */
        Bitmap[] flapAnim = new Bitmap[SPRITE_SHEET_COLS];
        for (int i =0;i<SPRITE_SHEET_COLS;i++) {
            flapAnim[i] = Bitmap.createBitmap(spriteSheet, (i* spriteWidth), 0, spriteWidth, spriteHeight);
        }
        this.flapAnimation = new Animation(flapAnim,0.2f);
        animationManager = new AnimationManager(flapAnimation);

        /* Paint for rectangle drawn around fish for debugging */
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void update() {

        /* Velocity increases with every update cycle */
        VELOCITY += GRAVITY;

        /* Distance updated according to velocity */
        distance = VELOCITY * 3.0f;

        /* y coordinate of fish is updated every cycle.
        . The fish rises if velocity is a negative value,
        . The fish falls if velocity is a positive value.
         */
        this.y +=  (int) distance;

        /* Angle is based on velocity, multiplied by an arbitrary value that works */
        angle = VELOCITY * 5.0f;

        /* Keep fish at top of screen if there */
        if (this.y <= 0 )  {
            this.y = 0;
        }
        /* If fish touches the base -> game over */
        else if (this.y >= SCREEN_HEIGHT*5/6 - spriteHeight){
            this.y = (SCREEN_HEIGHT*5/6 - spriteHeight);
            this.state = DEAD;
            //angle = 90;
        }

        /* Update the matrix which rotates the fish according to its current angle */
        this.rotate(angle);

        /* Update the animation manager, then move to the next frame of animation */
        animationManager.update();
        animationManager.playAction(0);

    }

    @Override
    public int getX() {

        return this.x;
    }

    @Override
    public int getY(){
        return this.y;
    }

    @Override
    public void draw(Canvas canvas){


        animationManager.draw(canvas,matrix);
        canvas.drawRect(this.x,this.y,this.x+spriteWidth,this.y+spriteHeight,paint);

    }

    private void rotate(float angle){

        angle = (angle>= 90) ? 90 : angle;

        this.matrix.reset();
        this.matrix.setRotate(angle,spriteWidth/2,spriteHeight/2);
        this.matrix.postTranslate(100,y);

    }

    public void jump()  {

        state = FLAP;
        VELOCITY = - 7.0f * SCALE;
    }

    public void resetAngle(){
        this.angle = 0;
        this.matrix.reset();
        this.matrix.setRotate(0,spriteWidth/2,spriteHeight/2);
        this.matrix.postTranslate(100,y);
    }

    public STATE getState() {

        return this.state;
    }

    public void setState(STATE state){
        this.state = state;
    }

    public float getWidth(){

        return spriteWidth;
    }

    public float getHeight(){

        return spriteWidth;
    }


}




