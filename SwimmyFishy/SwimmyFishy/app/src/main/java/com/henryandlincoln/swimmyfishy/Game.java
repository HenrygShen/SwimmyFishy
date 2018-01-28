package com.henryandlincoln.swimmyfishy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import static com.henryandlincoln.swimmyfishy.Fish.STATE.DEAD;

public class Game {

    private Context context;

    /* Variables relating to game objects */
    private int characterType;
    private Fish fish;
    private ArrayList<Pipe> pipes;
    private ArrayList<GameObject> objects;
    private int level;

    public Game(Context context) {

        this.context = context;
        this.pipes = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.level = 0;

    }

    public void createGame(int SCREEN_WIDTH, int SCREEN_HEIGHT){

        /* Create the player character */
        Bitmap fishBitMap = BitmapFactory.decodeResource(context.getResources(),characterType);
        fishBitMap = Bitmap.createScaledBitmap(fishBitMap, fishBitMap.getWidth() * SCREEN_WIDTH/1350,fishBitMap.getHeight() * SCREEN_HEIGHT/2400,false);
        fish =  new Fish(fishBitMap,100,SCREEN_HEIGHT/3,SCREEN_WIDTH,SCREEN_HEIGHT);

        /* Create the pipes */
        Bitmap pipeBitMap = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipes);
        pipeBitMap = Bitmap.createScaledBitmap(pipeBitMap, SCREEN_HEIGHT/3,SCREEN_HEIGHT,false);
        for (int i =0 ;i <2;i++){
            Pipe p  = new Pipe(pipeBitMap,i*SCREEN_WIDTH*7/8,0,SCREEN_WIDTH,SCREEN_HEIGHT);
            this.objects.add(p);
            pipes.add(p);
        }

    }

    public void updateGameState(){

        fish.update();
        pipes.get(0).update(pipes.get(1).getX()-10);
        pipes.get(1).update(pipes.get(0).getX());

    }

    public void drawObjects(Canvas canvas) {

        for (Pipe p : pipes){
            p.draw(canvas);
        }
        fish.draw(canvas);
    }

    public void checkCollisions(){

        for (GameObject object : objects){
            if (!object.offScreen()){
                fish.checkCollision(object);
            }
        }
    }

    public void setCharacterType(int character) {

        this.characterType = character;
    }

    public boolean gameOver(){
        return (this.getFish().getState() == DEAD);
    }

    public Fish getFish(){

        return this.fish;
    }


}
