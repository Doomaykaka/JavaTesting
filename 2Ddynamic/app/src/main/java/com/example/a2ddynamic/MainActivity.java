package com.example.a2ddynamic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Render2DLayoutView(this));
    }

    private class Render2DLayoutView extends SurfaceView implements SurfaceHolder.Callback {
        GraphThread surfaceThread = null;

        public Render2DLayoutView(Context context) {
            super(context);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            surfaceThread = new GraphThread(surfaceHolder);
            surfaceThread.start();
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

        }

        class GraphThread extends Thread{
            SurfaceHolder threadHolder = null;

            public GraphThread(SurfaceHolder newThreadHolder){
                threadHolder = newThreadHolder;
            }

            @Override
            public void run(){
                while(true){
                    try{
                        Canvas surfaceCanvas = threadHolder.lockCanvas();
                        surfaceCanvas.drawColor(Color.GREEN);
                        threadHolder.unlockCanvasAndPost(surfaceCanvas);
                        this.sleep(2000);
                        surfaceCanvas = threadHolder.lockCanvas();
                        surfaceCanvas.drawColor(Color.YELLOW);
                        threadHolder.unlockCanvasAndPost(surfaceCanvas);
                        this.sleep(2000);
                        surfaceCanvas = threadHolder.lockCanvas();
                        surfaceCanvas.drawColor(Color.RED);
                        threadHolder.unlockCanvasAndPost(surfaceCanvas);
                        this.sleep(2000);
                    }catch(InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }


}