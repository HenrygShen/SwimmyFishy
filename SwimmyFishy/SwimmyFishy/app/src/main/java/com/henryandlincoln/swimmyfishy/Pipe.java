package com.henryandlincoln.swimmyfishy;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Pipe extends GameObject {

    //int level
    GameView gameView;
    private Bitmap upPipe;
    private Bitmap downPipe;
    private int SCREEN_WIDTH;
    private boolean drawPipe;
    private long lastDrawTime;

    public Pipe(GameView gameView, Bitmap image, int x, int y, int SCREEN_HEIGHT,int SCREEN_WIDTH){

        super(image, 1, 2, x, y,SCREEN_HEIGHT);
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.gameView = gameView;
        this.x = SCREEN_WIDTH;
        drawPipe = false;
        lastDrawTime =  0;
        upPipe = createSubImageAt(0,0);
        downPipe = createSubImageAt(0,1);


    }


    /*public boolean detectCollision(){

    }*/


    public void update(){
        drawPipe = (lastDrawTime >= 3000);
        if (drawPipe){
            this.x -= 1;



        }
        else {
            lastDrawTime += (System.nanoTime()/1000000);
            Random rand = new Random();
            this.y = rand.nextInt(500) + 1;

        }


    }

    public void draw(Canvas canvas){
        if (drawPipe){
            canvas.drawBitmap(downPipe,x, y, null);
            canvas.drawBitmap(upPipe,x,y,null);
        }
    }
}
