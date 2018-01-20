package com.henryandlincoln.swimmyfishy;


import android.graphics.Bitmap;

public class Pipe extends GameObject {


    public Pipe(GameView gameView, Bitmap image, int x, int y, int SCREEN_HEIGHT){

        super(image, 1, 2, x, y,SCREEN_HEIGHT);


    }
}
