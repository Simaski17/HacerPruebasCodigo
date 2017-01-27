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

import java.util.ArrayList;

public class DrawLineActivity extends AppCompatActivity {


    AnimationView drawLine;
    public String hola;
    public String[] parts;
    ArrayList<Float> coordx = new ArrayList<Float>();
    ArrayList<Float> coordy = new ArrayList<Float>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        hola = getIntent().getStringExtra("partes");
        parts =hola.split("->"); // escape .

        drawLine = new AnimationView(this);
        drawLine.setBackgroundColor(Color.CYAN);
        setContentView(drawLine);
        drawLine.init();
    }


    public class AnimationView extends View {
        Path path;
        Paint paint;
        float length;

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

                new Pt(300, 400),

                new Pt(500, 100),

                new Pt(500, 400),

                new Pt(700, 200)

        };

        public void init()
        {
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(15);
            paint.setStyle(Paint.Style.STROKE);

            path = new Path();

            for(int i =0; i < parts.length; i++){
                Log.e("TAG","RECIBIDO: "+parts[i]);

                coordx.add(myPath[Integer.parseInt(parts[i])].x);
                coordy.add(myPath[Integer.parseInt(parts[i])].y);
            }

            path.moveTo(coordx.get(0), coordy.get(0));

            for (int i = 1; i < coordx.size(); i++){

                path.lineTo(coordx.get(i), coordy.get(i));

            }

            // Measure the path
            PathMeasure measure = new PathMeasure(path, false);
            length = measure.getLength();

            float[] intervals = new float[]{length, length};

            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "phase", 1.0f, 0.0f);
            animator.setDuration(7000);
            animator.start();

        }

        //is called by animtor object
        public void setPhase(float phase)
        {
            //Log.d("pathview","setPhase called with:" + String.valueOf(phase));
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
