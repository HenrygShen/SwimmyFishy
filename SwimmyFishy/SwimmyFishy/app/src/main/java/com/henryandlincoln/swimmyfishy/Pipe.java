package com.henryandlincoln.swimmyfishy;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Pipe extends GameObject {

    //int level
    GameView gameView;
    private Bitmap upPipe;
    private int upPipeY;
    private int downPipeY;
    private int upPipeHeight;
    private Bitmap downPipe;
    private int SCREEN_WIDTH;
    private boolean drawPipe;
    private long lastDrawTime;
    private boolean initialDraw;

    public Pipe(GameView gameView, Bitmap image, int x, int y, int SCREEN_HEIGHT,int SCREEN_WIDTH){

        super(image, 1, 2, x, y,SCREEN_HEIGHT);
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.gameView = gameView;
        this.x = x;
        drawPipe = false;
        initialDraw = true;
        lastDrawTime =  0;
        upPipe = createSubImageAt(0,0);
        downPipe = createSubImageAt(0,1);


    }


    public void update(){

        this.x -= 10;
        if (drawPipe) {
            if (initialDraw){
                Random rand = new Random();
                upPipeHeight = rand.nextInt(800) + 500 ;
                upPipeY = SCREEN_HEIGHT - upPipeHeight;
                downPipeY = upPipeY - height - 100;
                initialDraw = false;
            }
        }

        if (this.x <= - this.width){
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
