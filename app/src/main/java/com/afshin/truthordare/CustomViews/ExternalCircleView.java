package com.afshin.truthordare.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.R;

import java.io.IOException;

public class ExternalCircleView extends View {
    private Context context;
    private double centerX;
    private double centerY;
    private double radiusExternalCircle;
    private Paint externalCirclePaint;
    private Paint transitionArcPaint;
    private RectF transitionArcRect;
    private double radiusBigCircle;
    public ExternalCircleView(Context context) {
        super(context);
        this.context = context;
    }

    public ExternalCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ExternalCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public ExternalCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    private void init()
    {


        Typeface tf = Typeface.createFromAsset(context.getAssets(), "font/b_bardiya.ttf");
        Typeface.create(tf, Typeface.NORMAL);



        //Canvas
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;




        radiusBigCircle = (Math.min(getWidth(), getHeight()) / 2f )- 6d;
        radiusExternalCircle = radiusBigCircle ;






        transitionArcRect = new RectF();
        transitionArcRect.set(((float) (centerX - radiusExternalCircle )), ((float) (centerY - radiusExternalCircle )), ((float) (centerX + radiusExternalCircle )), ((float) (centerY + radiusExternalCircle )));






        transitionArcPaint = new Paint();
        transitionArcPaint.setAntiAlias(true);
        transitionArcPaint.setColor(context.getColor(R.color.flame));




        externalCirclePaint = new Paint();
        externalCirclePaint.setAntiAlias(true);
        externalCirclePaint.setColor(context.getColor(R.color.mauve));




    }


    boolean bottleIsPressed= false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        drawExternalCircle(canvas);
        drawTransitionArc(canvas);
    }
    float startAngleTransit = 0f;
    float swipeAngleTransit = 0.1f;
    int power = 0;
    private void drawTransitionArc(Canvas canvas) {
        Log.i("swipeAnglee", "drawTransitionArc: "+swipeAngleTransit);
        if (bottleIsPressed) {
            Log.i("drawTransitionArc", "drawTransitionArc: start:"+startAngleTransit+"swipe"+swipeAngleTransit);
            canvas.drawArc(transitionArcRect, startAngleTransit, swipeAngleTransit, true, transitionArcPaint);
            power += 40;
            swipeAngleTransit += 4f;
            if (startAngleTransit > 360f)
            {
                startAngleTransit = 0f;
                swipeAngleTransit = 0f;
                bottleIsPressed = false;

            }else{
                postInvalidateDelayed(1);
            }

        }
    }

    private void drawExternalCircle(Canvas canvas) {
        canvas.drawCircle(((float) centerX), ((float) centerY), ((float) (radiusExternalCircle)), externalCirclePaint);
    }


    public void onBottleActionDown(boolean down) {
        this.bottleIsPressed = down;
        if (!bottleIsPressed) {
            startAngleTransit = 0f;
            swipeAngleTransit = 0f;
        }
        postInvalidateDelayed(1);
    }
}
