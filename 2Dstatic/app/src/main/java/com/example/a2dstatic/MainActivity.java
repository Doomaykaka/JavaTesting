package com.example.a2dstatic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Render2DLayoutView(this));
    }

    private class Render2DLayoutView extends View {

        public Render2DLayoutView(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas c){
            super.onDraw(c);
            float x = c.getWidth();
            float y = c.getHeight();
            Paint pencil = new Paint();
            pencil.setColor(Color.BLACK);
            c.drawCircle(x/2, y/2, x/4, pencil);
            c.drawLine(x/8, y/8, x-(x/8), y/8, pencil);
            c.drawText("2D static render test", x/8, y-(y/8), pencil);
        }
    }
}