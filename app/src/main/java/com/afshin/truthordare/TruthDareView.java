package com.afshin.truthordare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TruthDareView extends View {
    private Paint arcPaint;
    private Paint namePaint;
    private Paint centralCirclePaint;
    private RectF arcRect;
    private Rect textRect;
    private double centerX;
    private double centerY;
    private double centerXBitmap;
    private double centerYBitmap;
    private double radiusBigCircle;
    private double radiusCentralCircle;
    private double mainScreenPadding;
    private List<Challenger> challengers = new ArrayList<>();
    private double startAngle;
    private double swipeAngle;
    private int numberOfChallengers;
    private boolean isInit = false;
    private double screenPadding;
    private double bottleAngle = 1;
    private Bitmap bmp;
    private Matrix bottleMatrix;
    private boolean isTouched;
    private int randomRotationNumber;
    private Challenger tempChallenger;


    public TruthDareView(Context context) {
        super(context);
        populateFake();

    }

    public TruthDareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        populateFake();

    }

    public TruthDareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        populateFake();


    }


    private void init() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.winebottle, options);

        bottleMatrix = new Matrix();
        //Canvas
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        //Bottle
        centerXBitmap = (getWidth() - bmp.getWidth()) / 2;
        centerYBitmap = (getHeight() - bmp.getHeight()) / 2;


        radiusBigCircle = Math.min(getWidth(), getHeight()) / 2;
        radiusCentralCircle = radiusBigCircle / 2;

        calculateSwipeAngle(challengers.size());
        calculateScreenPadding();


        arcRect = new RectF();
        Log.i("reces", "init: " + centerX + " " + centerY + " " + radiusBigCircle);
        arcRect.set(((float) (centerX - radiusBigCircle + screenPadding)), ((float) (centerY - radiusBigCircle + screenPadding)), ((float) (centerX + radiusBigCircle - screenPadding)), ((float) (centerY + radiusBigCircle - screenPadding)));


        textRect = new Rect();


        Log.i("rectDir", "init: top" + arcRect.top + "bottom" + arcRect.bottom + "left" + arcRect.left + "right" + arcRect.right);
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeWidth(0.5f);

        namePaint = new Paint();
        namePaint.setAntiAlias(true);

        centralCirclePaint = new Paint();
        centralCirclePaint.setAntiAlias(true);
        centralCirclePaint.setColor(Color.WHITE);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isInit)
            init();


        drawArcs(canvas);
        drawNames(canvas, challengers);
        drawCentralCircle(canvas);
        drawBottle(canvas);












    }

    private void drawArcs(Canvas canvas) {
        double startAngle = 0;
        for (int i = 0; i < challengers.size(); i++) {
            arcPaint.setColor(challengers.get(i).getColor());
            canvas.drawArc(arcRect, ((float) startAngle), ((float) swipeAngle), true, arcPaint);
            Log.i("drawArcss", "drawArcs: " + startAngle + " " + (swipeAngle + startAngle));
            challengers.get(i).setStartAngle(startAngle);
            challengers.get(i).setEndAngle(startAngle + swipeAngle);
            Log.i("anglesIn", "startAngle: " + challengers.get(i).getStartAngle() + "endAngle: " + challengers.get(i).getEndAngle());
            startAngle = startAngle + swipeAngle;

        }

    }

    private void drawNames(Canvas canvas, List<Challenger> challengers) {
        namePaint.setTextSize(20);

        for (Challenger challenger : challengers) {


            double angle = (challenger.getStartAngle() + challenger.getEndAngle()) / 2;

            Log.i("anglee", "drawNames: " + angle);
            int x = (int) (centerX + Math.cos(Math.toRadians(angle)) * (radiusBigCircle * 0.85));
            int y = (int) (centerY + Math.sin(Math.toRadians(angle)) * (radiusBigCircle * 0.85));
            Log.i("Cos", "drawNames: " + Math.cos(Math.toRadians(angle)) + " x,y:" + x + "," + y);
            textRect = new Rect();
//        textRect.set(100, y- challenger.getName().length(), x + challenger.getName().length()/2, y + challenger.getName().length()/2);
            namePaint.getTextBounds(challenger.getName(), 0, 0, textRect);
            Log.i("angleeeee", "drawNames: " + ((float) angle));
            canvas.save();
            canvas.rotate((float) (angle), x, y);
            canvas.drawText(challenger.getName(), x - 6 * (challenger.getName().length()), y, namePaint);
            canvas.restore();
        }

    }


    private void drawBottle(Canvas canvas) {
        Log.i("ANDLE", "drawBottle: "+bottleAngle);
        bottleMatrix.postRotate(((int) bottleAngle), bmp.getWidth() / 2, bmp.getHeight() / 2);
        bottleMatrix.postTranslate(((float) -(bmp.getWidth() / 2 - centerX)), -((float) (bmp.getHeight() / 2 - centerY)));

        // Set the current position to the updated rotation

//        int x = (int) (centerX + Math.cos(Math.toRadians(bottleAngle)) * (radiusBigCircle * 0.85));
//        int y = (int) (centerY + Math.sin(Math.toRadians(bottleAngle)) * (radiusBigCircle * 0.85));
//        canvas.drawLine(((float) centerX), ((float) centerY-y), x, y,arcPaint);

        canvas.drawBitmap(bmp, bottleMatrix, arcPaint);

        if (isTouched) {
            Log.i("randomRotationNumber", "onDraw: "+randomRotationNumber);
            Handler mainHandler = new Handler(getContext().getApplicationContext().getMainLooper());

            mainHandler.postDelayed(new Runnable()
            {
                public void run()
                {
                    Log.i("bottleAngle", "onDraw: "+bottleAngle);


                    if (bottleAngle<randomRotationNumber-randomRotationNumber/2) {
                        postInvalidateDelayed(10);
                        bottleAngle += 30;
                    }else if (bottleAngle<randomRotationNumber -randomRotationNumber/4){
                        postInvalidateDelayed(10);
                        bottleAngle += 20;
                    }else if (bottleAngle<randomRotationNumber -randomRotationNumber/6){
                        postInvalidateDelayed(10);
                        bottleAngle += 12;
                    }else if (bottleAngle<randomRotationNumber -randomRotationNumber/8){
                        postInvalidateDelayed(10);
                        bottleAngle += 10;
                    }else if (bottleAngle<randomRotationNumber -randomRotationNumber/10){
                        postInvalidateDelayed(10);
                        bottleAngle += 8;
                    }else if (bottleAngle<randomRotationNumber -randomRotationNumber/25){
                        postInvalidateDelayed(10);
                        bottleAngle += 5;
                    }else if (bottleAngle<randomRotationNumber -randomRotationNumber/30){
                        postInvalidateDelayed(10);
                        bottleAngle += 2;
                    }
                    else {
                        identifyWhoAreGoingToPlay(bottleAngle);
                        isTouched = false;


                    }
                }
            }, 1);


//            isTouched=false;


        }
//        bottleAngle += 30;
//        postInvalidateDelayed(10);


    }

    private void identifyWhoAreGoingToPlay(double bottleAngle) {
        double requesterAngle=bottleAngle % 360 -90;
        double responderAngle=bottleAngle % 360 +90;

        String requester="";
        String responder="";



        if (requesterAngle<0)
            requesterAngle+=360;

        if (responderAngle<0)
            responderAngle+=360;


        if (responderAngle>360)
            responderAngle=responderAngle%360;




        Log.i("bottleAngles", "identifyWhoIsGoingToBeAsked: "+requesterAngle + " "+responderAngle);
        for (Challenger challenger : challengers) {
            if (challenger.getStartAngle()<requesterAngle && challenger.getEndAngle()>requesterAngle){
                Log.i("bottleAngles", "identifyWhoIsGoingToBeAsked: "+challenger.getName());
                requester=challenger.getName();
            }
            if (challenger.getStartAngle()<responderAngle && challenger.getEndAngle()>responderAngle){
                responder=challenger.getName();
            }
        }
        Toast.makeText(getContext().getApplicationContext(),String.format("%1$s %2$s %3$s %4$s",requester,"از",responder,"بپرس"),Toast.LENGTH_LONG).show();
    }


    private void drawCentralCircle(Canvas canvas) {

        canvas.drawCircle(((float) centerX), ((float) centerY), ((float) (radiusCentralCircle)), centralCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("sdffe", "onTouchEvent: " + event.getAction());
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:



                bottleAngle=0;
                isTouched = true;
                randomRotationNumber = new Random().nextInt(500) + 2000;
                invalidate();
                return false;


            default:
                return true;
        }
    }

    public List<Challenger> getChallengers() {
        return challengers;
    }

    public void setChallengers(List<Challenger> challengers) {
        this.challengers = challengers;
    }

    private void calculateSwipeAngle(int numberOfChallengers) {
        Log.i("numberOfchallenger", "calculateSwipeAngle: " + numberOfChallengers);
        swipeAngle = ((360f / numberOfChallengers));
    }


    private void populateFake() {
        challengers.add(new Challenger("علی", generateRandomColor()));
        challengers.add(new Challenger("رضا", generateRandomColor()));
        challengers.add(new Challenger("حسن", generateRandomColor()));
        challengers.add(new Challenger("وحید", generateRandomColor()));
        challengers.add(new Challenger("حمید", generateRandomColor()));
        challengers.add(new Challenger("نوید", generateRandomColor()));
        challengers.add(new Challenger("افشین", generateRandomColor()));
        challengers.add(new Challenger("پوریا", generateRandomColor()));
        challengers.add(new Challenger("مجید", generateRandomColor()));
        challengers.add(new Challenger("کامران", generateRandomColor()));




    }

    public String getPath() {
        return TruthDareView.class.getCanonicalName();
    }

    private void calculateScreenPadding() {

        screenPadding = radiusBigCircle / 20;

    }

    private int generateRandomColor() {
        ArrayList<Integer> colors = new ArrayList<>();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        if (colors.contains(color)) {
            return generateRandomColor();
        } else {
            colors.add(color);
            return color;
        }
    }


}
