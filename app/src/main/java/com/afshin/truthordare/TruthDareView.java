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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.VelocityTrackerCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


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
    //Timer
    private TimerTask timerTask;
    private Timer timer;
    private Runnable update;
    private Handler handler;
//    private static final long DELAY_MS = 2000;
//    private static final long PERIOD_MS = 10000;

    final float scale = getResources().getDisplayMetrics().density;
    public TruthDareView(Context context) {
        super(context);


    }

    public TruthDareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public TruthDareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



    }


    private void init() {
        Log.i("scale", "init: "+scale);

        BitmapFactory.Options options = new BitmapFactory.Options();
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.winebottle, options);
        bottleMatrix = new Matrix();

        //Canvas
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        //Bottle
        centerXBitmap = (getWidth() - bmp.getWidth()) / 2;
        centerYBitmap = (getHeight() - bmp.getHeight()) / 2;


        radiusBigCircle = (Math.min(getWidth(), getHeight()) / 2);
        radiusCentralCircle = radiusBigCircle / 2;

        calculateSwipeAngle(challengers.size());
        calculateScreenPadding();


        arcRect = new RectF();
//        arcRect.set(((float) (radiusCentralCircle * Math.cos(swipeAngle)))/4, ((float) (radiusCentralCircle * Math.sin(swipeAngle)))/4, ((float) (radiusCentralCircle * Math.cos(swipeAngle) + radiusCentralCircle))/4, ((float) (radiusCentralCircle * Math.sin(swipeAngle) + radiusCentralCircle))/4);
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
            Log.i("drawArcs", "drawArcs: " + startAngle + " " + (swipeAngle + startAngle));
            challengers.get(i).setStartAngle(startAngle);
            challengers.get(i).setEndAngle(startAngle + swipeAngle);
            Log.i("drawArcs", "startAngle: " + challengers.get(i).getStartAngle() + "endAngle: " + challengers.get(i).getEndAngle());
            startAngle = startAngle + swipeAngle;
        }

    }

    private void drawNames(Canvas canvas, List<Challenger> challengers) {
        namePaint.setTextSize(20);

        for (Challenger challenger : challengers) {
            double angle = (challenger.getStartAngle() + challenger.getEndAngle()) / 2;
            Log.i("drawNames", "drawNames: " + angle);
            int x = (int) (centerX + Math.cos(Math.toRadians(angle)) * (radiusBigCircle * 0.85));
            int y = (int) (centerY + Math.sin(Math.toRadians(angle)) * (radiusBigCircle * 0.85));
            Log.i("drawNames", "cos: " + Math.cos(Math.toRadians(angle)) + " x,y:" + x + "," + y);
            textRect = new Rect();
            namePaint.getTextBounds(challenger.getName(), 0, 0, textRect);
            Log.i("drawNames", "drawNames: " + ((float) angle));
            canvas.save();
            canvas.rotate((float) (angle), x, y);
            canvas.drawText(challenger.getName(), x - 6 * (challenger.getName().length()), y, namePaint);
            canvas.restore();
        }

    }


    private void drawBottle(Canvas canvas) {
        Log.i("drawBottle", "drawBottle: " + bottleAngle);
        bottleMatrix.postRotate(((int) bottleAngle), bmp.getWidth() / 2, bmp.getHeight() / 2);
        bottleMatrix.setScale(((float) radiusCentralCircle), ((float) radiusCentralCircle));
        bottleMatrix.postTranslate(((float) -(bmp.getWidth() / 2 - centerX)), -((float) (bmp.getHeight() / 2 - centerY)));


        canvas.drawBitmap(bmp, bottleMatrix, arcPaint);

        if (isTouched) {

            Log.i("drawBottle", "onDraw: " + randomRotationNumber);
            Handler mainHandler = new Handler(getContext().getApplicationContext().getMainLooper());

            mainHandler.postDelayed(new Runnable() {
                public void run() {
                    Log.i("drawBottle", "onDraw: " + bottleAngle);
                    if (bottleAngle < randomRotationNumber - randomRotationNumber / 2) {
                        postInvalidateDelayed(10);
                        bottleAngle += 30;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 4) {
                        postInvalidateDelayed(10);
                        bottleAngle += 20;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 6) {
                        postInvalidateDelayed(10);
                        bottleAngle += 12;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 8) {
                        postInvalidateDelayed(10);
                        bottleAngle += 10;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 10) {
                        postInvalidateDelayed(10);
                        bottleAngle += 8;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 25) {
                        postInvalidateDelayed(10);
                        bottleAngle += 5;
                    } else if (bottleAngle < randomRotationNumber - randomRotationNumber / 30) {
                        postInvalidateDelayed(10);
                        bottleAngle += 2;
                    } else {
                        identifyWhoAreGoingToPlay(bottleAngle);
                        isTouched = false;
                    }
                }
            }, 1);


        }


    }

    private void identifyWhoAreGoingToPlay(double bottleAngle) {
        double requesterAngle = bottleAngle % 360 - 90;
        double responderAngle = bottleAngle % 360 + 90;

        String requester = "";
        String responder = "";


        if (requesterAngle < 0)
            requesterAngle += 360;

        if (responderAngle < 0)
            responderAngle += 360;


        if (responderAngle > 360)
            responderAngle = responderAngle % 360;


        Log.i("bottleAngles", "identifyWhoIsGoingToBeAsked: " + requesterAngle + " " + responderAngle);
        for (Challenger challenger : challengers) {
            if (challenger.getStartAngle() < requesterAngle && challenger.getEndAngle() > requesterAngle) {
                Log.i("bottleAngles", "identifyWhoIsGoingToBeAsked: " + challenger.getName());
                requester = challenger.getName();

            }
            if (challenger.getStartAngle() < responderAngle && challenger.getEndAngle() > responderAngle) {
                responder = challenger.getName();

            }


        }
        if (requester.isEmpty() || responder.isEmpty())
            Toast.makeText(getContext().getApplicationContext(), String.format("دوباره بچرخون"), Toast.LENGTH_LONG).show();
         else
        Toast.makeText(getContext().getApplicationContext(), String.format("%1$s %2$s %3$s %4$s", requester, "از", responder, "بپرس"), Toast.LENGTH_LONG).show();
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
                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.

                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                firstTouchTime=System.currentTimeMillis();
                firstTouchX = event.getX();
                firstTouchY = event.getY();



                Log.i("onTouchEvent", "firstTouchTime: "+firstTouchTime);
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
                bottleAngle = 0;
                isTouched = true;
                randomRotationNumber = new Random().nextInt(500) + 2000;
                invalidate();
                break;
            default:
                break;
        }
        return true;
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

    public int generateRandomColor() {
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




    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}
