package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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

    private boolean drawPipe;

    private boolean initialDraw;

    private final int UP_LIMIT;
    private final int DOWN_LIMIT;

    public Pipe(Bitmap image, int x, int y, int SCREEN_WIDTH,int SCREEN_HEIGHT){

        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.x = x;
        drawPipe = false;
        initialDraw = true;
        spriteWidth = image.getWidth()/2;
        spriteHeight = image.getHeight();

        upPipe = Bitmap.createBitmap(image,0*image.getWidth()/2,0,spriteWidth,spriteHeight);
        downPipe = Bitmap.createBitmap(image,image.getWidth()/2,0,spriteWidth,spriteHeight);

        DOWN_LIMIT = SCREEN_HEIGHT /4;
        UP_LIMIT = SCREEN_HEIGHT * 3/5;
    }

    public void update(int xPos){

        this.x -= 10;
        if (drawPipe) {
            if (initialDraw){
                Random rand = new Random();
                upPipeY = rand.nextInt(UP_LIMIT) + DOWN_LIMIT;
                downPipeY = (int)(upPipeY -  spriteHeight* 1.2);
                initialDraw = false;
            }
        }

        if (this.x <= - spriteWidth){
            this.x = xPos + SCREEN_WIDTH * 7/8;
            drawPipe = true;
            initialDraw = true;
        }

    }

    @Override
    public void draw(Canvas canvas){
        if (drawPipe){
            canvas.drawBitmap(downPipe,x, downPipeY, null);
            canvas.drawBitmap(upPipe,x,upPipeY,null);
        }
    }

    @Override
    public void update(){

    }
    @Override
    public int getX(){
        return this.x;
    }
}
