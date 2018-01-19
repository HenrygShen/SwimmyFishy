package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Fish extends GameObject {

    private static final int ROW_UP_ACTION = 0;
    private static final int ROW_DOWN_ACTION = 1;
    private static final int ROW_RECOVER_ACTION = 2;
    private static final int ROW_FALL_ACTION = 3;


    private int row;
    private int col;

    public static float VELOCITY;
    public static float GRAVITY;
    private float lastDrawNanoTime;
    private boolean falling;

    private Bitmap[] notFlapping;
    private Bitmap[] flapping;
    private Bitmap[] fall;
    private Bitmap[] recover;

    private GameView gameView;

    public Fish(GameView gameView, Bitmap image, int x, int y,int SCREEN_HEIGHT) {

        super(image, 4, 4, x, y,SCREEN_HEIGHT);
        this.row = 0;
        this.col = 0;
        falling = false;
        lastDrawNanoTime = -1 ;
        VELOCITY =  0.1f;
        GRAVITY = 0.1f;

        this.gameView= gameView;
        this.notFlapping = new Bitmap[colCount];
        this.flapping = new Bitmap[colCount];
        this.recover = new Bitmap[colCount];
        this.fall = new Bitmap[colCount];

        this.row = ROW_UP_ACTION;

        for (int col =0;col<this.colCount;col++){
            this.notFlapping[col] =  this.createSubImageAt(ROW_DOWN_ACTION,col);
            this.flapping[col] = this.createSubImageAt(ROW_UP_ACTION,col);
            this.fall[col] = this.createSubImageAt(ROW_FALL_ACTION,col);
            this.recover[col] = this.createSubImageAt(ROW_RECOVER_ACTION,col);
        }
    }

    public Bitmap[] getMoveBitmaps() {
        switch (row)  {
            case ROW_DOWN_ACTION:
                return  this.notFlapping;
            case ROW_UP_ACTION:
                return this.flapping;
            case ROW_FALL_ACTION:
                return this.fall;
            case ROW_RECOVER_ACTION:
                return this.recover;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap() {

        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.col];
    }


    public void update() {

        this.col++;

        if(col >= this.colCount)  {
            this.col = 0;
        }

        long now = System.nanoTime();
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        int timeDifference = (int) ((now - lastDrawNanoTime)/ 1000000 );

        VELOCITY += GRAVITY;
        float distance = VELOCITY * timeDifference;


        this.y = this.y + (int) distance;

        falling = (VELOCITY >= 0);


        /* Keep object at edge of screen if there */
        if (this.y <= 0 )  {
            this.y = 0;
        }
        else if (this.y >= SCREEN_HEIGHT - height){
            this.y = SCREEN_HEIGHT - height;
            VELOCITY = 0.f;
            GRAVITY = 0.f;
        }
        else {
            GRAVITY =  0.1f;
        }


        if (!falling) {
            this.row = ROW_UP_ACTION;
        }
        else if (falling && ((this.row == ROW_DOWN_ACTION) || (this.row == ROW_FALL_ACTION))) {
            this.row = ROW_FALL_ACTION;
        }
        else if (falling && (this.row == ROW_UP_ACTION)){
            this.row = ROW_DOWN_ACTION;
        }
        else if (!falling  && (this.row == ROW_FALL_ACTION)){
            this.row = ROW_RECOVER_ACTION;
        }
        else {
            this.row = ROW_UP_ACTION;
        }


    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void jump()  {

       VELOCITY = - 1.1f;
    }
}




