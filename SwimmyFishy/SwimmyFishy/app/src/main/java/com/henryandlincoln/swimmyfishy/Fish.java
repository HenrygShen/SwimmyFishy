package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Fish extends GameObject {

    private static final int ROW_NOT_FLAPPING = 0;
    private static final int ROW_FLAPPING = 1;

    private int row;
    private int col;

    public static final float VELOCITY = 0.1f;

    private int movingVectorY = 5;

    private long lastDrawNanoTime =-1;

    private Bitmap[] notFlapping;
    private Bitmap[] flapping;


    private GameView gameView;

    public Fish(GameView gameView, Bitmap image, int x, int y) {

        super(image, 2, 2, x, y);
        this.row = 0;
        this.col = 0;

        this.gameView= gameView;
        this.notFlapping = new Bitmap[colCount];
        this.flapping = new Bitmap[colCount];

        for (int col =0;col<this.colCount;col++){
            this.notFlapping[col] =  this.createSubImageAt(ROW_NOT_FLAPPING,col);
            this.flapping[col] = this.createSubImageAt(ROW_FLAPPING,col);
        }


    }

    public void update()  {

        this.col++;
        if(col >= this.colCount)  {
            this.col =0;
        }
        // Current time in nanoseconds
        long now = System.nanoTime();

        /* On the first draw */
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }

        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int timeDifference = (int) ((now - lastDrawNanoTime)/ 1000000 );

        /* Distance moved */
        float distance = VELOCITY * timeDifference;

        double movingVectorLength = movingVectorY;

        // Calculate the new position of the game character.
        this.y = y +  (int)(distance* movingVectorY / movingVectorLength);

        // When the game's character touches the top or bottom of the screen.
        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = - this.movingVectorY;
        } else if(this.y > this.gameView.getHeight()- height)  {
            this.y= this.gameView.getHeight()- height;
            this.movingVectorY = - this.movingVectorY ;
        }

        /* Which row of sprite sheet to use */
        if(movingVectorY > 0 ) {
            this.row = ROW_FLAPPING;
        }
        else if(movingVectorY < 0) {
            this.row = ROW_NOT_FLAPPING;
        }
        else  {
            this.row = ROW_NOT_FLAPPING;
        }
    }

    public Bitmap[] getMoveBitmaps()  {
        switch (row)  {
            case ROW_NOT_FLAPPING:
                return  this.notFlapping;
            case ROW_FLAPPING:
                return this.flapping;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.col];
    }

    public void draw(Canvas canvas) {

        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        /* Last draw time */
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void setMovingVector(int movingVectorY)  {
        this.movingVectorY = movingVectorY;
    }


}




