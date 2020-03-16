package com.example.color.colorfullamp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;


/**
 * TODO: document your custom view class.
 */
public class LampView2 extends View {


    private int width;
    private int height;
    private float animatedValue;
    private int animatedValueAlpha;
    private float lengthOfColor = 200f;
    private float mLength;
    private Path path;
    private final int mTimeMoving = 2000;
    private final int mTimeAlta = 1000;

    private int mAlpha;
    private final String TAG = "LampView";

    public LampView2(Context context) {
        super(context);

    }


    public LampView2(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public LampView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipRect(0, 0, width, height);
        canvas.clipRect(50, 50, width - 50, height - 50, Region.Op.DIFFERENCE);
        int[] colors = {Color.YELLOW, Color.GREEN, Color.RED, Color.GREEN, Color.RED, Color.BLUE};
        float[] position = {0f, 0.1f, 0.3f, 0.5f, 0.8f, 1.0f};
        LinearGradient backGradient = new LinearGradient(0, 0, lengthOfColor, 25, colors, position, Shader.TileMode.REPEAT);
        Paint mPaintMovingColor = new Paint();
        mPaintMovingColor.setShader(backGradient);


//        canvas.translate(animatedValue / 100 * width/**/, 0);
        canvas.drawRect(-width, 0, width, 25, mPaintMovingColor);///1

        canvas.drawRect(-width, height-25, width, height, mPaintMovingColor);////3
        canvas.save();

//        canvas.drawRect(-height, 0, height, 25, mPaintMovingColor);
        canvas.translate(width, 0);
        canvas.rotate(90);
        canvas.drawRect(-height, 0, height, 25, mPaintMovingColor);////2

        canvas.drawRect(-height, width, height, width-25, mPaintMovingColor);////4

        canvas.restore();

        canvas.save();
        canvas.translate(-height, 0);

        canvas.restore();


    }

    public void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        Log.i(TAG, "construct,width:" + width + ", height:" + height);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setDuration(mTimeMoving).setRepeatCount(ValueAnimator.INFINITE);//动画循环时间
        animator.setRepeatMode(ValueAnimator.RESTART); //动画循环方式
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
        ValueAnimator animatorAlpha = ValueAnimator.ofInt(50, 255);
        animatorAlpha.setDuration(mTimeAlta).setRepeatCount(ValueAnimator.INFINITE);
        animatorAlpha.setRepeatMode(ValueAnimator.REVERSE);
        animatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValueAlpha = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animatorAlpha.start();

    }

    private float durationSplitPath(float splitPathLength) {

        return splitPathLength / mLength * mTimeMoving;
    }


}
