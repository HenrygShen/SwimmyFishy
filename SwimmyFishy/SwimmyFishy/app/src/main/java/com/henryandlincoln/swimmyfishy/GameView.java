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

    private Fish playerCharacter;
    private ArrayList<Pipe> pipes = new ArrayList<>();
    private Bitmap bg = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg);
    private Bitmap bg_base = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg_base);
    private int SCREEN_HEIGHT;
    private int SCREEN_WIDTH;
    private boolean firstTouch;

    private Game game;

    private Paint paint;
    private String avgFps;

    public GameView(Context context,Game game) {

        super(context);
        this.game = game;
        this.setFocusable(true);
        this.getHolder().addCallback(this);
        this.gameThread = new GameThread(this,this.getHolder());
        firstTouch = true;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);


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

        Bitmap fishBitMap = BitmapFactory.decodeResource(this.getResources(),R.drawable.catfish);
        bg = Bitmap.createScaledBitmap(bg,this.getWidth(),this.getHeight(),false);
        Bitmap pipeBitMap = BitmapFactory.decodeResource(this.getResources(),R.drawable.pipes);

        bg_base  = Bitmap.createScaledBitmap(bg_base,SCREEN_WIDTH,SCREEN_HEIGHT/6,false);
        pipeBitMap = Bitmap.createScaledBitmap(pipeBitMap, SCREEN_HEIGHT/3,SCREEN_HEIGHT,false);
        for (int i =0 ;i <2;i++){
            Pipe p  = new Pipe(pipeBitMap,i*this.getWidth()*7/8,0,SCREEN_WIDTH,SCREEN_HEIGHT);
            pipes.add(p);
        }
        this.playerCharacter = new Fish(fishBitMap,100,500,SCREEN_WIDTH,SCREEN_HEIGHT);

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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setAvgFps(String avgFps) {

        this.avgFps = avgFps;
    }

    public void stopThread(){
        boolean retry= true;
        if (this.gameThread.isAlive()) {
            while (retry) {
                try {
                    this.gameThread.setRunning(false);
                    this.gameThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayFps(Canvas canvas, String fps) {

        if (canvas != null && fps != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(fps, this.getWidth() - 100, 100, paint);
        }
    }

}