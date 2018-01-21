package com.henryandlincoln.swimmyfishy;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.text.DecimalFormat;
import android.util.Log;

public class GameThread extends Thread {


    private static final String TAG = GameThread.class.getSimpleName();
    private final static int    MAX_FPS = 50;
    private final static int    MAX_FRAME_SKIPS = 5;
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;
    private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
    private final static int    STAT_INTERVAL = 1000; //ms
    private final static int    FPS_HISTORY_NR = 10;
    private long lastStatusStore = 0;
    private long statusIntervalTimer    = 0l;
    private long totalFramesSkipped         = 0l;
    private long framesSkippedPerStatCycle  = 0l;
    private int frameCountPerStatCycle = 0;
    private long totalFrameCount = 0l;
    private double  fpsStore[];
    private long    statsCount = 0;
    private double  averageFps = 0.0;
    private boolean running;
    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameView gameView, SurfaceHolder surfaceHolder)  {
        this.gameView= gameView;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        Canvas canvas;

        Log.d(TAG, "Starting game loop");

        // initialise timing elements for stat gathering
        initTimingElements();
        long beginTime;     // the time when the cycle begun
        long timeDiff;      // the time it took for the cycle to execute
        int sleepTime;      // ms to sleep (<0 if we're behind)
        int framesSkipped;  // number of frames being skipped

        while (running) {
            canvas = null;

            try {

                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {

                    beginTime = System.currentTimeMillis();

                    framesSkipped = 0;  // resetting the frames skipped

                    // update game state
                    this.gameView.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    this.gameView.draw(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);
                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            Thread.sleep(sleepTime);

                        }
                        catch (InterruptedException e) {
                        }

                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {


                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }

                    if (framesSkipped > 0) {

                        Log.d(TAG, "Skipped:" + framesSkipped);
                    }

                    // for statistics
                    framesSkippedPerStatCycle += framesSkipped;
                    // calling the routine to store the gathered statistics
                    storeStats();
                }
            }
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void storeStats(){

        frameCountPerStatCycle++;
        totalFrameCount++;

        // check the actual time
        statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

        if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {

            // calculate the actual frames pers status check interval
            double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));
            //stores the latest fps in the array
            fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;
            // increase the number of times statistics was calculated
            statsCount++;
            double totalFps = 0.0;

            // sum up the stored fps values
            for (int i = 0; i < FPS_HISTORY_NR; i++) {
                totalFps += fpsStore[i];
            }

            // obtain the average
            if (statsCount < FPS_HISTORY_NR) {

                // in case of the first 10 triggers
                averageFps = totalFps / statsCount;
            }
            else {
                averageFps = totalFps / FPS_HISTORY_NR;
            }
            // saving the number of total frames skipped
            totalFramesSkipped += framesSkippedPerStatCycle;

            // resetting the counters after a status record (1 sec)
            framesSkippedPerStatCycle = 0;
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;
            statusIntervalTimer = System.currentTimeMillis();
            lastStatusStore = statusIntervalTimer;

            Log.d(TAG, "Average FPS:" + df.format(averageFps));

            gameView.setAvgFps("FPS: " + df.format(averageFps));

        }
    }


    private void initTimingElements() {
        // initialise timing elements
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++) {
            fpsStore[i] = 0.0;
        }
        Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialised");
    }



    public void setRunning(boolean running)  {

        this.running= running;
    }
}