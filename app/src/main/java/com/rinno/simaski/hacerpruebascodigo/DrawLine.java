package com.rinno.simaski.hacerpruebascodigo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by simaski on 26/01/17.
 */

class DrawLine extends View {
    Paint paint = new Paint();
    public DrawLine(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(50, 100, 600, 600, paint);
        canvas.drawLine(50, 550, 770, 0, paint);
    }

}