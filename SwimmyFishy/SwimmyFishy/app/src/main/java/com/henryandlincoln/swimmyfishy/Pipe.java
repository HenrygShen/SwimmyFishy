package com.henryandlincoln.swimmyfishy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Pipe implements GameObject {

    GameView gameView;
    private Bitmap upPipe;
    private int upPipeY;
    private int downPipeY;
    private int x;
    private int y;
    private int upPipeHeight;
    private Bitmap downPipe;
    private int SCREEN_WIDTH;
    private boolean drawPipe;
    private int pipeWidth;
    private int pipeHeight;
    private boolean initialDraw;
    private int SCREEN_HEIGHT;

    public Pipe(Bitmap image, int x, int y, int SCREEN_WIDTH,int SCREEN_HEIGHT){

        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.gameView = gameView;
        this.x = x;
        drawPipe = false;
        initialDraw = true;
        pipeWidth = image.getWidth()/2;
        pipeHeight = image.getHeight()/2;


        upPipe = Bitmap.createBitmap(image,0*image.getWidth()/2,0,image.getWidth()/2,image.getHeight());
        downPipe = Bitmap.createBitmap(image,image.getWidth()/2,0,image.getWidth()/2,image.getHeight());

    }


    public void update(){

        this.x -= 10;
        if (drawPipe) {
            if (initialDraw){
                Random rand = new Random();
                upPipeHeight = rand.nextInt(800) + 500 ;
                upPipeY = SCREEN_HEIGHT - upPipeHeight;
                downPipeY = upPipeY - pipeHeight - 100;
                initialDraw = false;
            }
        }

        if (this.x <= - pipeWidth){
            drawPipe = true;
            this.x = SCREEN_WIDTH;
            initialDraw = true;
        }

    }

    public void draw(Canvas canvas){
        if (drawPipe){
            canvas.drawBitmap(downPipe,x, downPipeY, null);
            canvas.drawBitmap(upPipe,x,upPipeY,null);
        }
    }
}
