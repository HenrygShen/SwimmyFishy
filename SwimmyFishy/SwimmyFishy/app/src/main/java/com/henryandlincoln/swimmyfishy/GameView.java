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
    private Fish fish;
    private Pipe pipe;
    private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    Bitmap bg = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg);
    Bitmap bg_base = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg_base);

    Paint paint;
    private String avgFps;

    public GameView(Context context) {

        super(context);

        this.setFocusable(true);

        this.getHolder().addCallback(this);


    }

    public void update()  {

       for (Pipe p : pipes){
           p.update();
       }
        this.fish.update();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            this.fish.jump();

            return true;
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
        canvas.drawBitmap(bg_base,0,this.getHeight()-200,null);
        this.fish.draw(canvas);
        displayFps(canvas, avgFps);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Bitmap fishBitMap = BitmapFactory.decodeResource(this.getResources(),R.drawable.catfish_sprite_low);
        bg = Bitmap.createScaledBitmap(bg,this.getWidth(),this.getHeight(),false);
        Bitmap pipeBitMap = BitmapFactory.decodeResource(this.getResources(),R.drawable.pipes);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        bg_base  =Bitmap.createScaledBitmap(bg_base,this.getWidth(),200,false);
        for (int i =0 ;i <2;i++){
            Pipe p  = new Pipe(this,pipeBitMap,i*this.getWidth()*7/8,0,this.getHeight(),this.getWidth());
            pipes.add(p);
        }
        this.fish = new Fish(this,fishBitMap,100,500,this.getHeight());

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);
                this.gameThread.join();
            }
            catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= false;
        }
    }

    public void setAvgFps(String avgFps) {

        this.avgFps = avgFps;
    }

    private void displayFps(Canvas canvas, String fps) {

        if (canvas != null && fps != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(fps, this.getWidth() - 100, 100, paint);
        }
    }

}