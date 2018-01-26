package com.henryandlincoln.swimmyfishy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {


    private GameThread gameThread;
    private Game game;

    private Fish playerCharacter;
    private ArrayList<Pipe> pipes = new ArrayList<>();
    private Bitmap bg_base;
    private Paint paint;

    private int SCREEN_HEIGHT;
    private int SCREEN_WIDTH;
    private boolean firstTouch;

    /* FOR DEBUGGING */
    private String avgFps;

    public GameView(Context context,Game game) {

        super(context);

        /* Make it so that this view can handle events */
        this.setFocusable(true);
        this.getHolder().addCallback(this);

        /* Create thread to manage updates and drawing to the canvas */
        this.gameThread = new GameThread(this,this.getHolder());

        /* Save the game state to load it in this game view */
        this.game = game;

        /* Set this variable to stall game thread and only start after the first touch */
        firstTouch = true;


    }

    public void update()  {

       pipes.get(0).update(pipes.get(1).getX()-10);
       pipes.get(1).update(pipes.get(0).getX());
       playerCharacter.update();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (firstTouch){
                firstTouch = false;
                this.gameThread.setRunning(true);
                this.gameThread.start();
                this.playerCharacter.jump();
                return true;
            }
            else {
                this.playerCharacter.jump();
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)  {

        super.draw(canvas);

        canvas.drawPaint(paint);

        for (Pipe p : pipes){
            p.draw(canvas);
        }

        playerCharacter.draw(canvas);
        canvas.drawBitmap(bg_base,0,SCREEN_HEIGHT*5/6,null);

        //displayFps(canvas, avgFps);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.SCREEN_HEIGHT = this.getHeight();
        this.SCREEN_WIDTH = this.getWidth();

        setBackgrounds();
        loadFishCharacter();
        loadPipes();
        gameThread.firstDraw();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry= true;
        if (this.gameThread.isAlive()) {
            while (retry) {
                try {
                    this.gameThread.setRunning(false);
                    this.gameThread.join();
                    retry = false;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void stopThread(){

        boolean retry= true;
        if (this.gameThread.isAlive()) {
            while (retry) {
                try {
                    this.gameThread.setRunning(false);
                    this.gameThread.join();
                    retry = false;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void loadFishCharacter(){

        int characterChoice = this.game.getCharacter();
        Bitmap fishBitMap = BitmapFactory.decodeResource(this.getResources(),characterChoice);
        this.playerCharacter = new Fish(fishBitMap,100,SCREEN_HEIGHT*1/3,SCREEN_WIDTH,SCREEN_HEIGHT);
    }

    private void loadPipes(){

        Bitmap pipeBitMap = BitmapFactory.decodeResource(this.getResources(),R.drawable.pipes);
        pipeBitMap = Bitmap.createScaledBitmap(pipeBitMap, SCREEN_HEIGHT/3,SCREEN_HEIGHT,false);
        for (int i =0 ;i <2;i++){
            Pipe p  = new Pipe(pipeBitMap,i*this.getWidth()*7/8,0,SCREEN_WIDTH,SCREEN_HEIGHT);
            pipes.add(p);
        }
    }

    private void setBackgrounds(){

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        bg_base  = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg_base);
        bg_base  = Bitmap.createScaledBitmap(bg_base,SCREEN_WIDTH,SCREEN_HEIGHT/6,false);
    }

    public Fish getCharacter(){

        return this.playerCharacter;
    }

    /* FOR DEBUGGING */
    private void displayFps(Canvas canvas, String fps) {

        if (canvas != null && fps != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(fps, this.getWidth() - 100, 100, paint);
        }
    }

    /* FOR DEBUGGING */
    public void setAvgFps(String avgFps) {

        this.avgFps = avgFps;
    }

}