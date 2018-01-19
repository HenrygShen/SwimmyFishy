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

    public static final float VELOCITY = 0.01f;

    private float lastDrawNanoTime = -1;
    private int jumpDestination = 0 ;
    private int movingVectorY = 10;
    private boolean falling = false;

    private Bitmap[] notFlapping;
    private Bitmap[] flapping;

    private GameView gameView;

    public Fish(GameView gameView, Bitmap image, int x, int y,int SCREEN_HEIGHT) {

        super(image, 4, 4, x, y,SCREEN_HEIGHT);
        this.row = 0;
        this.col = 0;

        this.gameView= gameView;
        this.notFlapping = new Bitmap[colCount];
        this.flapping = new Bitmap[colCount];

        for (int col =0;col<this.colCount;col++){
            this.notFlapping[col] =  this.createSubImageAt(ROW_DOWN_ACTION,col);
            this.flapping[col] = this.createSubImageAt(ROW_UP_ACTION,col);
        }
    }

    public Bitmap[] getMoveBitmaps() {
        switch (row)  {
            case ROW_DOWN_ACTION:
                return  this.notFlapping;
            case ROW_UP_ACTION:
                return this.flapping;
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
            this.col =0;
        }

        long now = System.nanoTime();
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        int timeDifference = (int) ((now - lastDrawNanoTime)/ 1000000 );
        float distance = VELOCITY * timeDifference;
        if (jumpDestination == 0 ){
            this.falling = true;
        }

        if (this.y > jumpDestination){
            this.y = this.y - (int) distance;
            falling = false;
        }
        else {
            this.y = this.getY() + (int) (distance * 0.1);
            falling = true;
        }
        /* Keep object at edge of screen if there */
        if (this.y < 0 )  {
            this.y = 0;
        }
        else if (this.y > SCREEN_HEIGHT){
            this.y = SCREEN_HEIGHT;
        }


        if (!falling && ((this.row == ROW_UP_ACTION) || (this.row == ROW_RECOVER_ACTION))) {
            this.row = ROW_UP_ACTION;
        }
        else if (falling && (this.row == ROW_UP_ACTION)) {
            this.row = ROW_DOWN_ACTION;
        }
        else if (falling && ((this.row == ROW_DOWN_ACTION) || (this.row == ROW_FALL_ACTION))){
            this.row = ROW_FALL_ACTION;
        }
        else if (!falling  && (this.row == ROW_FALL_ACTION)){
            this.row = ROW_RECOVER_ACTION;
        }


    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
       // this.lastDrawNanoTime= System.nanoTime();
    }

    public void jump()  {

        jumpDestination = this.y - 50;
    }
}




