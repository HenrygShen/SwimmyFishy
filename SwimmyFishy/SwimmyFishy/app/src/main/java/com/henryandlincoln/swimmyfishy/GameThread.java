package com.henryandlincoln.swimmyfishy;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running;
    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameView gameView, SurfaceHolder surfaceHolder)  {
        this.gameView= gameView;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime = System.nanoTime();

        while(running)  {
            Canvas canvas= null;
            try {
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas();
                // Synchronized
                synchronized (canvas) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }

            }
            catch(Exception e)  {
                // Do nothing.
            }
            finally {
                if(canvas!= null)  {
                    // Unlock Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime() ;
            // Interval to redraw game
            // (Change nanoseconds to milliseconds)
            long waitTime = (now - startTime)/1000000;
            if (waitTime < 20)  {
                waitTime= 20; // Millisecond.
            }

            try {
                this.sleep(waitTime);
            }
            catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
        }
    }

    public void setRunning(boolean running)  {

        this.running= running;
    }
}