package com.afshin.truthordare.CustomViews;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.core.view.VelocityTrackerCompat;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class TruthDareView extends View {
    private Context context;
    private ITruthDare iTruthDare;
    private Paint arcPaint;
    private Paint transitionArcPaint;
    private Paint namePaint;
    private Paint centralCirclePaint;
    private Paint enlargingCirclePaint;
    private Paint externalCirclePaint;
    private RectF arcRect;
    private RectF transitionArcRect;
    private Rect textRect;
    private double centerX;
    private double centerY;
    private double centerXBitmap;
    private double centerYBitmap;
    private double radiusBigCircle;
    private double radiusCentralCircle;
    private double radiusExternalCircle;
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
    private boolean isReleased = false;
    private boolean isPressed = false;
    private int randomRotationNumber;
    //Timer
    private TimerTask timerTask;
    private Timer timer;
    private Runnable update;
    private Handler handler;
    private Bitmap  bottleBitmap;
    //power for swiping the bottle
    private int power;
    private boolean bottleIsTurning;
    private byte[] chosenBottle;
    private boolean imageFilled = false;
    private boolean transitionArcFinished = false;

//    private static final long DELAY_MS = 2000;
//    private static final long PERIOD_MS = 10000;

    final float scale = getResources().getDisplayMetrics().density;
    public TruthDareView(Context context) {
        super(context);
        this.context = context;
    }

    public TruthDareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TruthDareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,byte[] image) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.chosenBottle = image;
    }

    public void setITruthDare(ITruthDare iTruthDare){
        this.iTruthDare = iTruthDare;
    }


    private void init(byte[] image)
    {


        Typeface tf = Typeface.createFromAsset(context.getAssets(), "font/b_bardiya.ttf");
        Typeface.create(tf, Typeface.NORMAL);
        for (Challenger challenger : challengers) {
            if (challenger.getImage()!= null) {
                Bitmap bitmap = null;
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), challenger.getImage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap resizedBmp = getCroppedScaledBitmap(bitmap, 440, 440);
                challenger.setImageCustom(resizedBmp);
            }
        }

        Log.i("TruthDareView", "init: "+scale);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 1;
        if (image!=null)
        bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

        Log.i("TruthDareView", "init: "+bmp);
        bottleMatrix = new Matrix();
        if (bmp!=null)
        bottleBitmap = Bitmap.createScaledBitmap(bmp,(bmp.getWidth()/2 ),(bmp.getHeight()/2),true);


        //Canvas
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;



        //Bottle
        if (bmp!=null) {
            centerXBitmap = (getWidth() - bmp.getWidth()) / 2f;
            centerYBitmap = (getHeight() - bmp.getHeight()) / 2f;
            Log.i("TruthDareView", "init: centerxBitmap:" + centerXBitmap + "centerYBitmap:" + centerYBitmap);
        }

        radiusBigCircle = (Math.min(getWidth(), getHeight()) / 2f )- 6d;
        radiusCentralCircle = radiusBigCircle / 2;
        radiusExternalCircle = radiusBigCircle ;

        calculateSwipeAngle(challengers.size());
        calculateScreenPadding();


        arcRect = new RectF();
//        arcRect.set(((float) (radiusCentralCircle * Math.cos(swipeAngle)))/4, ((float) (radiusCentralCircle * Math.sin(swipeAngle)))/4, ((float) (radiusCentralCircle * Math.cos(swipeAngle) + radiusCentralCircle))/4, ((float) (radiusCentralCircle * Math.sin(swipeAngle) + radiusCentralCircle))/4);
        Log.i("reces", "init: " + centerX + " " + centerY + " " + radiusBigCircle);
        arcRect.set(((float) (centerX - radiusBigCircle + screenPadding)), ((float) (centerY - radiusBigCircle + screenPadding)), ((float) (centerX + radiusBigCircle - screenPadding)), ((float) (centerY + radiusBigCircle - screenPadding)));


        transitionArcRect = new RectF();
        transitionArcRect.set(((float) (centerX - radiusExternalCircle )), ((float) (centerY - radiusExternalCircle )), ((float) (centerX + radiusExternalCircle )), ((float) (centerY + radiusExternalCircle )));


        textRect = new Rect();


        Log.i("rectDir", "init: top" + arcRect.top + "bottom" + arcRect.bottom + "left" + arcRect.left + "right" + arcRect.right);
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeWidth(0.5f);


        transitionArcPaint = new Paint();
        transitionArcPaint.setAntiAlias(true);
        transitionArcPaint.setColor(context.getColor(R.color.flame));

        namePaint = new Paint();
        namePaint.setAntiAlias(true);
        namePaint.setTextSize(40);
        namePaint.setTypeface(tf);

        centralCirclePaint = new Paint();
        centralCirclePaint.setAntiAlias(true);
        centralCirclePaint.setColor(context.getColor(R.color.specPurpleLight));

        externalCirclePaint = new Paint();
        externalCirclePaint.setAntiAlias(true);
        externalCirclePaint.setColor(context.getColor(R.color.mauve));


        enlargingCirclePaint = new Paint();
        enlargingCirclePaint.setAntiAlias(true);
        enlargingCirclePaint.setColor(context.getColor(R.color.flame));

    }

    public void changeBottleBitmap(byte[] image)
    {
        this.chosenBottle = image;
        imageFilled = true;
        postInvalidateDelayed(1);
    }

private boolean bottleActionDown;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (imageFilled) {
//        if (!bottleActionDown) {
            init(chosenBottle);
//            drawExternalCircle(canvas);

            drawArcs(canvas);
            drawNames(canvas, challengers);
            drawImages(canvas, challengers);
            drawCentralCircle(canvas);
//        }else {
////            drawTransitionArc(canvas);
//        }
//            drawEnlargingCircle(canvas);
//        }
    }
    float startAngleTransit = 0f;
    float swipeAngleTransit = 0.1f;

    private void drawTransitionArc(Canvas canvas) {
        if (isPressed) {
            Log.i("drawTransitionArc", "drawTransitionArc: start:"+startAngleTransit+"swipe"+swipeAngleTransit);
            canvas.drawArc(transitionArcRect, startAngleTransit, swipeAngleTransit, true, transitionArcPaint);
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

        for (Challenger challenger : challengers)
        {
            double angle = (challenger.getStartAngle() + challenger.getEndAngle()) / 2;
            Log.i("drawNames", "drawNames: " + angle);
            int x = (int) (centerX + Math.cos(Math.toRadians(angle)) * (radiusBigCircle * 0.85));
            int y = (int) (centerY + Math.sin(Math.toRadians(angle)) * (radiusBigCircle * 0.85));
            Log.i("drawNames", "cos: " + Math.cos(Math.toRadians(angle)) + " x,y:" + x + "," + y);
            textRect = new Rect();
            namePaint.getTextBounds(challenger.getName(), 0, 0, textRect);
            Log.i("drawNames", "drawNames: " + ((float) angle));
            canvas.save();
            canvas.rotate((float) (angle) + 90f, x, y);
            canvas.drawText(challenger.getName(), x - 6 * (challenger.getName().length()), y, namePaint);
            canvas.restore();
        }

    }

    private void drawImages(Canvas canvas, List<Challenger> challengers)  {
    try {

        for (Challenger challenger : challengers) {
            double angle = (challenger.getStartAngle() + challenger.getEndAngle()) / 2;
            Log.i("drawImages", "drawImages: " + angle);
            int x = (int) (centerX + Math.cos(Math.toRadians(angle)) * (radiusBigCircle * 0.65));
            int y = (int) (centerY + Math.sin(Math.toRadians(angle)) * (radiusBigCircle * 0.65));
            Log.i("drawImages", "cos: " + Math.cos(Math.toRadians(angle)) + " x,y:" + x + "," + y);
            textRect = new Rect();
            namePaint.getTextBounds(challenger.getName(), 0, 0, textRect);
            Log.i("drawImages", "drawImages: " + ((float) angle));
            canvas.save();
//            canvas.rotate((float) (angle), x, y);


            canvas.drawBitmap(challenger.getImageCustom(), x - 60 , y - 50, namePaint);
//            canvas.drawText(challenger.getName(), x - 6 * (challenger.getName().length()), y, namePaint);
            canvas.restore();
        }
    }catch(Exception e){
        Log.i(TAG, "drawImages: "+e.getMessage());
    }

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



    public void identifyWhoAreGoingToPlay(double bottleAngle) {
        double responderAngle = bottleAngle % 360 - 90;
        double requesterAngle = bottleAngle % 360 + 90;

        Challenger requester = null;
        Challenger responder = null;


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
                requester = challenger;

            }
            if (challenger.getStartAngle() < responderAngle && challenger.getEndAngle() > responderAngle) {
                responder = challenger;

            }


        }
//        if (requester.isEmpty() || responder.isEmpty())
//            Toast.makeText(getContext().getApplicationContext(), String.format("دوباره بچرخون"), Toast.LENGTH_LONG).show();
//         else {
//            Toast.makeText(getContext().getApplicationContext(), String.format("%1$s %2$s %3$s %4$s", requester, "از", responder, "بپرس"), Toast.LENGTH_LONG).show();
//        }
         iTruthDare.onResult(requester , responder);
    }




    private void drawCentralCircle(Canvas canvas) {
        canvas.drawCircle(((float) centerX), ((float) centerY), ((float) (radiusCentralCircle)), centralCirclePaint);
    }
    private void drawExternalCircle(Canvas canvas) {
        canvas.drawCircle(((float) centerX), ((float) centerY), ((float) (radiusExternalCircle)), externalCirclePaint);
    }
    private double radiusEnlargingCircle = radiusCentralCircle/30;
    private void drawEnlargingCircle(Canvas canvas) {
        if (transitionArcFinished) {
            if (radiusEnlargingCircle < radiusCentralCircle) {

                radiusEnlargingCircle += 1d;
                canvas.drawCircle(((float) centerX), ((float) centerY), ((float) (radiusEnlargingCircle)), enlargingCirclePaint);
                postInvalidateDelayed(1);
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
    boolean isTouch = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
           return false;
//
//        isTouch = true;
//        if (bottleIsTurning)
//            return false;
//        Log.i("onTouchEvent", "onTouchEvent: " + event.getAction());
//        float firstTouchX = 0;
//        float firstTouchY = 0;
//        long firstTouchTime= 0;
//        VelocityTracker mVelocityTracker = VelocityTracker.obtain();
//
//        int index = event.getActionIndex();
//        int action = event.getActionMasked();
//        int pointerId = event.getPointerId(index);
//
//
//        float endTouchX = 0;
//        float endTouchY = 0;
//        long endTouchTime = 0;
//
//        long timeLapTouches = 0;
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                isPressed = true;
//                postInvalidateDelayed(1);
//
//                if(mVelocityTracker == null) {
//                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
//
//                }
//                else {
//                    // Reset the velocity tracker back to its initial state.
//                    mVelocityTracker.clear();
//                }
//                // Add a user's movement to the tracker.
//                mVelocityTracker.addMovement(event);
//                firstTouchTime = System.currentTimeMillis();
//                firstTouchX = event.getX();
//                firstTouchY = event.getY();
//
//
//
//                Log.i("onTouchEvent", "ACTION_DOWN "+firstTouchTime);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mVelocityTracker.addMovement(event);
//                // When you want to determine the velocity, call
//                // computeCurrentVelocity(). Then call getXVelocity()
//                // and getYVelocity() to retrieve the velocity for each pointer ID.
//                mVelocityTracker.computeCurrentVelocity(1000);
//                // Log velocity of pixels per second
//                // Best practice to use VelocityTrackerCompat where possible.
//                Log.d("ACTION_MOVE", "X velocity: " +
//                        VelocityTrackerCompat.getXVelocity(mVelocityTracker,
//                                pointerId));
//                Log.d("ACTION_MOVE", "Y velocity: " +
//                        VelocityTrackerCompat.getYVelocity(mVelocityTracker,
//                                pointerId));
//                break;
//            case MotionEvent.ACTION_UP:
//                transitionArcFinished = false;
//                radiusEnlargingCircle = radiusCentralCircle / 30;
//                swipeAngleTransit = 0f;
//                isPressed = false;
//                mVelocityTracker.recycle();
//                endTouchTime=System.currentTimeMillis();
//                Log.i("onTouchEvent", "endTouchTime: "+endTouchTime);
//                endTouchX = event.getX();
//                endTouchY = event.getY();
////                double distance = ( Math.sqrt((endTouchX - firstTouchX) * (endTouchY - firstTouchX) + (endTouchY - firstTouchY) * (firstTouchY - endTouchY)));
//                double distance = Math.sqrt((endTouchX-firstTouchX) * (endTouchX-firstTouchX) + (endTouchY-firstTouchY) * (endTouchY-endTouchY));
//                double hypotDistance = Math.hypot(endTouchX - firstTouchX, endTouchY - firstTouchY);
//                timeLapTouches = endTouchTime-firstTouchTime;
//                Log.i("onTouchEvent", " distance :"+distance+"hypotDistance"+hypotDistance+""+timeLapTouches);
//                double speed = distance / timeLapTouches;
//                Log.i("speed", "speed: "+speed);
//                bottleAngle = bottleAngle % 360;
//                isReleased = true;
//                Log.i("power", "power: "+power);
//                randomRotationNumber = power + 30;
//                power = 0;
//                invalidate();
//                break;
//            default:
//                break;
//        }
//        return true;
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
        challengers.add(new Challenger("علی",null, generateRandomColor()));
        challengers.add(new Challenger("رضا",null, generateRandomColor()));
        challengers.add(new Challenger("حسن",null, generateRandomColor()));
        challengers.add(new Challenger("وحید",null, generateRandomColor()));
        challengers.add(new Challenger("حمید",null, generateRandomColor()));
        challengers.add(new Challenger("نوید",null, generateRandomColor()));
        challengers.add(new Challenger("افشین",null, generateRandomColor()));
        challengers.add(new Challenger("پوریا",null, generateRandomColor()));
        challengers.add(new Challenger("مجید",null, generateRandomColor()));
        challengers.add(new Challenger("کامران",null, generateRandomColor()));
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
        int color = Color.argb(255, rnd.nextInt(200)+40, rnd.nextInt(200)+40, rnd.nextInt(200)+40);

        if (colors.contains(color)) {
            return generateRandomColor();
        } else {
            colors.add(color);
            return color;
        }
    }

    public void onBottleActionDown(boolean down) {
               this.bottleActionDown = down;
               if (down){
                   postInvalidateDelayed(1);
               }
    }

    public interface ITruthDare{
        void onResult(Challenger requester, Challenger responder );
        void onPressed(boolean b);
   }




    public Bitmap getResizedBitmap(Bitmap bmp, int newHeight, int newWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public Bitmap getCroppedScaledBitmap(Bitmap bitmap, int newHeight, int newWidth) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        int width = output.getWidth();
        int height = output.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(output, 0, 0, width, height, matrix, false);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(output, 100, 100, false);
        //return _bmp;
        return resizedBitmap;
    }


}
