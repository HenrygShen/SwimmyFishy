package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Fish extends GameObject {

    private static final int ROW_UP_ACTION = 0;
    private static final int ROW_DOWN_ACTION = 1;


    private int row;
    private int col;

    public static final float VELOCITY = 0.1f;

    private int movingVectorY = 20;
    private int movingVectorX = 10;

    private long lastDrawNanoTime =-1;

    private Bitmap[] notFlapping;
    private Bitmap[] flapping;

    private GameView gameView;

    public Fish(GameView gameView, Bitmap image, int x, int y) {

        super(image, 2, 4, x, y);
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

        /* Current time in nanoseconds */
        long now = System.nanoTime();

        /* First draw */
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        /* Convert to milliseconds */
        int timeDifference = (int) ((now - lastDrawNanoTime)/ 1000000 );

        /* Distance moved */
        float distance = VELOCITY * timeDifference;

        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);

        // Calculate the new position of the game character.
        this.y = y +  (int)(distance * movingVectorY / movingVectorLength);

        // When the game's character touches the edge of the screen, then change direction
        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = - this.movingVectorY;
        } else if(this.y > this.gameView.getHeight()- height)  {
            this.y= this.gameView.getHeight()- height;
            this.movingVectorY = - this.movingVectorY ;
        }


        if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
            this.row = ROW_UP_ACTION;
        }
        else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
            this.row = ROW_DOWN_ACTION;
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

    public void setMovingVector(int movingVectorX, int movingVectorY)  {
        this.movingVectorX= movingVectorX;
        this.movingVectorY = movingVectorY;
    }
}




