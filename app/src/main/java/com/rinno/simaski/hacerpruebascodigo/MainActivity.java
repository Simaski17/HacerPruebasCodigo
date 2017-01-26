package com.rinno.simaski.hacerpruebascodigo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    AnimationView drawLine;
    String hola = "aqui";


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            drawLine = new AnimationView(this);
            drawLine.setBackgroundColor(Color.CYAN);
            setContentView(drawLine);
            drawLine.init();
        }


    /*class PathLine extends View {
        Paint paint = new Paint();
        public PathLine(Context context) {
            super(context);
            paint.setColor(Color.BLACK);
        }
        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawLine(50, 100, 600, 600, paint);
            canvas.drawLine(50, 550, 770, 0, paint);
            Log.e("TAG","AQUI: "+hola);
        }

    }*/


    public class AnimationView extends View {
        Path path;
        Paint paint;
        float length;
        public String partes;

        public AnimationView(Context context)
        {
            super(context);
        }

        public AnimationView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }

        public AnimationView(Context context, AttributeSet attrs, int defStyleAttr)
        {
            super(context, attrs, defStyleAttr);
        }

        class Pt{
            float x, y;
            Pt(float _x, float _y){
                x = _x;
                y = _y;
            }
        }



        Pt[] myPath = { new Pt(200, 200),

                new Pt(300, 100),

                new Pt(400, 200),

                new Pt(500, 100),

                new Pt(600, 200),

                new Pt(700, 100),

                new Pt(900, 100),

                new Pt(900, 500),

                new Pt(200, 500),

                new Pt(200, 200)

        };

        public void init()
        {
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);

            path = new Path();

            path.moveTo(myPath[0].x, myPath[0].y);

            for (int i = 1; i < myPath.length; i++){

                path.lineTo(myPath[i].x, myPath[i].y);

            }

            // Measure the path
            PathMeasure measure = new PathMeasure(path, false);
            length = measure.getLength();

            float[] intervals = new float[]{length, length};

            ObjectAnimator animator = ObjectAnimator.ofFloat(AnimationView.this, "phase", 1.0f, 0.0f);
            animator.setDuration(7000);
            animator.start();

            Log.e("TAG","RECIBIDO: "+hola);
        }

        //is called by animtor object
        public void setPhase(float phase)
        {
            Log.d("pathview","setPhase called with:" + String.valueOf(phase));
            paint.setPathEffect(createPathEffect(length, phase, 0.0f));
            invalidate();//will calll onDraw
        }

        private PathEffect createPathEffect(float pathLength, float phase, float offset)
        {
            return new DashPathEffect(new float[] {
                    pathLength, pathLength
            },
                    Math.max(phase * pathLength, offset));
        }

        @Override
        public void onDraw(Canvas c)
        {
            super.onDraw(c);
            c.drawPath(path, paint);
        }
    }

}




