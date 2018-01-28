package com.henryandlincoln.swimmyfishy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {


    private GameThread gameThread;
    private Game game;

    private Bitmap bg_base;
    private Paint paint;

    private int SCREEN_HEIGHT;
    private int SCREEN_WIDTH;
    private boolean firstTouch;

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

        if (!game.gameOver()){
            game.updateGameState();
        }
        else {
            /* Show end of game screen */
            ((Activity) this.getContext()).finish();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (firstTouch){
                firstTouch = false;
                this.gameThread.setRunning(true);
                this.gameThread.start();
                game.getFish().jump();
                return true;
            }
            else {
                game.getFish().jump();
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)  {

        super.draw(canvas);

        canvas.drawPaint(paint);
        game.drawObjects(canvas);
        canvas.drawBitmap(bg_base,0,SCREEN_HEIGHT*5/6,null);
        game.checkCollisions();

    }

    private void setBackgrounds(){

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        bg_base  = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg_base);
        bg_base  = Bitmap.createScaledBitmap(bg_base,SCREEN_WIDTH,SCREEN_HEIGHT/6,false);
    }

    public Fish getCharacter(){

        return game.getFish();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.SCREEN_HEIGHT = this.getHeight();
        this.SCREEN_WIDTH = this.getWidth();

        setBackgrounds();
        game.createGame(SCREEN_WIDTH,SCREEN_HEIGHT);
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

}