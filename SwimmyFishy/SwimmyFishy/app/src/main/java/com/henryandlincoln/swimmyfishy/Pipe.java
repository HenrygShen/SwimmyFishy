package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class Pipe implements GameObject {

    private Bitmap upPipe;
    private Bitmap downPipe;

    private int upPipeY;
    private int downPipeY;
    private final int spriteWidth;
    private final int spriteHeight;
    private final int SCREEN_WIDTH;

    private int x;

    private final float SCALE;

    private boolean drawPipe;
    private boolean initialDraw;

    private Rectangle fish;
    private Rectangle upRect;
    private Rectangle downRect;

    private Random rand;

    private final int UP_LIMIT;
    private final int DOWN_LIMIT;

    private Paint paint;

    public Pipe(Bitmap image, int x, int y, int SCREEN_WIDTH,int SCREEN_HEIGHT){


        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.x = x;
        drawPipe = false;
        initialDraw = true;
        spriteWidth = image.getWidth()/2;
        spriteHeight = image.getHeight();

        /* Create random number generator */
        rand = new Random();

        /* Create rectangles to compare for object collision */
        fish = new Rectangle();
        upRect = new Rectangle();
        downRect = new Rectangle();

        /* The pipe moving in the x axis will have its speed adjusted according to the screen width */
        this.SCALE = SCREEN_WIDTH/1080.f;

        /* Create an image each of the pipe facing up and facing down, from the original sprite sheet */
        upPipe = Bitmap.createBitmap(image,0*image.getWidth()/2,0,spriteWidth,spriteHeight);
        downPipe = Bitmap.createBitmap(image,image.getWidth()/2,0,spriteWidth,spriteHeight);

        /* Set the limits of how high the pipes will be */
        DOWN_LIMIT = SCREEN_HEIGHT /4;
        UP_LIMIT = SCREEN_HEIGHT * 3/5;

        /* Create paint for debugging (for object collision algorithm) */
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void update(int xPos){

        /* Pipe moves at a velocity that is relative to the screen width */
        this.x -= 10*SCALE;

        /* Sets up the pipe to be drawn with random heights */
        if (drawPipe) {
            if (initialDraw){
                upPipeY = rand.nextInt(UP_LIMIT) + DOWN_LIMIT;
                downPipeY = (int)(upPipeY -  spriteHeight* 1.2);
                initialDraw = false;
            }
        }

        /* Resets the pipe to be drawn starting off screen */
        if (this.x <= - spriteWidth){
            this.x = xPos + SCREEN_WIDTH * 7/8;
            drawPipe = true;
            initialDraw = true;
        }
    }


    @Override
    public void draw(Canvas canvas){

        if (drawPipe){
            /* Draw both pipes */
            canvas.drawBitmap(downPipe,x, downPipeY, null);
            canvas.drawBitmap(upPipe,x,upPipeY,null);

            /* Draw rectangle for debugging */
            if (this.x >= 0 && this.x <= SCREEN_WIDTH) {
                canvas.drawRect(downRect.x, downRect.y, downRect.x + downRect.width, downRect.y + downRect.height, paint);
                canvas.drawRect(upRect.x, upRect.y, upRect.x + upRect.width, upRect.y + upRect.height, paint);
            }
        }
    }

    @Override
    public int getX(){
        return this.x;
    }

    @Override
    public int getY(){
        return this.upPipeY;
    }


    public boolean checkCollision(int x,int y){

        upRect.x = this.x;
        upRect.y = upPipeY;

        downRect.x = this.x;
        downRect.y = downPipeY;

        fish.x = x;
        fish.y = y;

        return (upRect.intersects(fish) || downRect.intersects(fish));

    }

    public void setUpCollisionChecking(float fishWidth,float fishHeight){

        upRect.width = spriteWidth;
        upRect.height = spriteHeight;

        downRect.width = spriteWidth;
        downRect.height = spriteHeight;

        fish.width = fishWidth;
        fish.height = fishHeight;
    }

    public boolean offScreen(){

        return this.initialDraw;
    }

    @Override
    public void update(){

    }

}
