package com.henryandlincoln.swimmyfishy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {


    private GameThread gameThread;

    private Fish fish;

    public GameView(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
        this.fish.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x=  (int)event.getX();
            int y = (int)event.getY();

            int movingVectorX = x - this.fish.getX() ;
            int movingVectorY = y - this.fish.getY() ;

            this.fish.setMovingVector(movingVectorX,movingVectorY);
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        this.fish.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Bitmap fishBitMap = BitmapFactory.decodeResource(this.getResources(),R.drawable.catfish_sprite_low);
        this.fish = new Fish(this,fishBitMap,100,500);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);
                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }
            catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= false;
        }
    }

}