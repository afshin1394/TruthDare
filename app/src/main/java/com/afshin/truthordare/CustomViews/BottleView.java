package com.afshin.truthordare.CustomViews;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.VelocityTrackerCompat;

import com.afshin.truthordare.Challenger;

import java.util.ArrayList;
import java.util.List;


public class BottleView extends View {
    private BottleView.IBottle iBottle;
    private Matrix bottleMatrix;
    private Bitmap bmp;
    private Bitmap  bottleBitmap;
    private double bottleAngle = 1;
    private boolean isReleased = false;
    private boolean bottleIsTurning;
    private int randomRotationNumber;
    private double radiusCentralCircle;
    private double radiusBigCircle;
    private Paint arcPaint;
    private double centerX;
    private double centerY;
    private List<Challenger> challengers = new ArrayList<>();
    private int power;
    float swipeAngleTransit = 0.1f;
    private boolean imageFilled = false;
    private byte[] chosenBottle;

    public BottleView(Context context) {
        super(context);
    }

    public BottleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,byte[] image) {
        super(context, attrs, defStyleAttr);
        this.chosenBottle = image;

    }



    private void init(byte[] image) {
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;
        arcPaint = new Paint();
        bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        radiusBigCircle = (Math.min(getWidth(), getHeight()) / 2f )- 6d;

        radiusCentralCircle = radiusBigCircle / 2;
        Log.i("BottleView", "init: " + bmp +"centerX:"+centerX+"centerY:"+centerY+"radiusBigCircle:"+radiusBigCircle+"radiusCentralCircle:"+radiusCentralCircle);
        bottleMatrix = new Matrix();
        bottleBitmap = Bitmap.createScaledBitmap(bmp, (bmp.getWidth() / 2), (bmp.getHeight() / 2), true);
        Log.i("BottleView", "init: "+bottleBitmap);
    }
    public void setIBottle(IBottle iBottle){
        this.iBottle = iBottle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("onDraw", "onDraw: "+imageFilled);
        if(imageFilled) {
            init(chosenBottle);
            drawBottle(canvas);
            drawTransitionArc(canvas);
        }
    }
    float startAngleTransit = 0f;
    private void drawTransitionArc(Canvas canvas) {
        if (isPressed) {
//            Log.i("drawTransitionArc", "drawTransitionArc: start:"+startAngleTransit+"swipe"+swipeAngleTransit);
//            canvas.drawArc(transitionArcRect, startAngleTransit, swipeAngleTransit, true, transitionArcPaint);
            power += 40;
            swipeAngleTransit += 4f;
            if (startAngleTransit > 360f)
            {
                isPressed = false;
                swipeAngleTransit = 0f;
            }else{
                postInvalidateDelayed(1);
            }

        }
    }

    public void changeBottleBitmap(byte[] image)
    {

        this.chosenBottle = image;
        imageFilled = true;
        postInvalidateDelayed(1);

    }
   boolean isPressed = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        if (bottleIsTurning)
            return false;
        Log.i("onTouchEvent", "onTouchEvent: " + event.getAction());
        float firstTouchX = 0;
        float firstTouchY = 0;
        long firstTouchTime= 0;
        VelocityTracker mVelocityTracker = VelocityTracker.obtain();

        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);


        float endTouchX = 0;
        float endTouchY = 0;
        long endTouchTime = 0;

        long timeLapTouches = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                iBottle.onBottleActionDown(true);
                postInvalidateDelayed(1);
                isPressed = true;

                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.

                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                firstTouchTime = System.currentTimeMillis();
                firstTouchX = event.getX();
                firstTouchY = event.getY();



                Log.i("onTouchEvent", "ACTION_DOWN "+firstTouchTime);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                Log.d("ACTION_MOVE", "X velocity: " +
                        VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                                pointerId));
                Log.d("ACTION_MOVE", "Y velocity: " +
                        VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                                pointerId));
                break;
            case MotionEvent.ACTION_UP:
                iBottle.onBottleActionDown(false);
                swipeAngleTransit = 0f;
                mVelocityTracker.recycle();
                endTouchTime=System.currentTimeMillis();
                Log.i("onTouchEvent", "endTouchTime: "+endTouchTime);
                endTouchX = event.getX();
                endTouchY = event.getY();
//                double distance = ( Math.sqrt((endTouchX - firstTouchX) * (endTouchY - firstTouchX) + (endTouchY - firstTouchY) * (firstTouchY - endTouchY)));
                double distance = Math.sqrt((endTouchX-firstTouchX) * (endTouchX-firstTouchX) + (endTouchY-firstTouchY) * (endTouchY-endTouchY));
                double hypotDistance = Math.hypot(endTouchX - firstTouchX, endTouchY - firstTouchY);
                timeLapTouches = endTouchTime-firstTouchTime;
                Log.i("onTouchEvent", " distance :"+distance+"hypotDistance"+hypotDistance+""+timeLapTouches);
                double speed = distance / timeLapTouches;
                Log.i("speed", "speed: "+speed);
                bottleAngle = bottleAngle % 360;
                isReleased = true;
                isPressed = false;
                Log.i("power", "power: "+power);
                randomRotationNumber = power + 30;
                power = 0;
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }





    private void drawBottle(Canvas canvas) {

        Log.i("drawBottle", "drawBottle: " + bottleAngle);
        bottleMatrix.postRotate(((int) bottleAngle), ((float) (bmp.getWidth() / 2)), ((float) (bmp.getHeight() / 2)));
        Log.i("drawBottle", "drawBottle: " + radiusCentralCircle);
//        bottleMatrix.postScale(((float) bmp.getWidth()/2), ((float) bmp.getWidth()/2));
        bottleMatrix.postTranslate((((float) - (bmp.getWidth() / 2  - centerX))), -((float) (bmp.getHeight() / 2 - centerY)));
        Log.i(TAG, "drawBottle: " + radiusCentralCircle);
        Log.i("drawBottle", "drawBottle: "+bottleMatrix+"bmp"+bmp+"arcPaint"+arcPaint);
        canvas.drawBitmap(bmp, bottleMatrix, arcPaint);
        Log.i("isReleased", "drawBottle: "+isReleased);
        if (isReleased) {
            bottleIsTurning = true;

            Log.i("drawBottle", "onDraw: " + randomRotationNumber);
            Handler mainHandler = new Handler(getContext().getApplicationContext().getMainLooper());

            mainHandler.postDelayed(new Runnable() {
                public void run() {
                    Log.i("drawBottle", "onDraw: " + bottleAngle);
                    if (bottleAngle < randomRotationNumber - randomRotationNumber / 2f) {
                        postInvalidateDelayed(1);
                        bottleAngle += 30;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 4f) {
                        postInvalidateDelayed(1);
                        bottleAngle += 20;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 6f) {
                        postInvalidateDelayed(1);
                        bottleAngle += 12;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 8f) {
                        postInvalidateDelayed(1);
                        bottleAngle += 10;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 10f) {
                        postInvalidateDelayed(1);
                        bottleAngle += 8;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 15f) {
                        postInvalidateDelayed(1);
                        bottleAngle += 6;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 20f) {
                        postInvalidateDelayed(1);
                        bottleAngle += 4;
                    } else {


                        bottleIsTurning = false;

                        identifyWhoAreGoingToPlay(bottleAngle);
                        isReleased = false;
                    }
                }
            }, 1);


        }


    }


    private void identifyWhoAreGoingToPlay(double bottleAngle) {
        iBottle.identifyWhoAreGoingToPlay(bottleAngle);
    }
    public void setChallengers(List<Challenger> challengers) {
        this.challengers = challengers;
    }

    public interface IBottle{
        void identifyWhoAreGoingToPlay(double bottleAngle);

        void onBottleActionDown(boolean b);
    }
}