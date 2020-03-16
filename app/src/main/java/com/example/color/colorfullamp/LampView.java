package com.example.color.colorfullamp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
public class LampView extends View {


    private float startAngle = 0;
    private int width;
    private int height;
    private float animatedValue;
    private int animatedValueAlpha;
    private float lengthOfColor = 200f;
    private float mLength;
    private Path path;
    private final int mTimeMoving = 5000;
    private final int mTimeAlta = 1000;

    private int mAlpha;
    private final String TAG = "LampView";

    public LampView(Context context) {
        super(context);

    }


    public LampView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public LampView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int[] colors = {Color.YELLOW,Color.GREEN, Color.RED, Color.GREEN,Color.RED,Color.BLUE};
        float[] position = {0f,0.1f, 0.3f,0.5f,0.8f, 1.0f};

        Paint mPaintBackground = new Paint();             // 创建画笔
        mPaintBackground.setColor(Color.GREEN);           // 画笔颜色
        mPaintBackground.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边

        mPaintBackground.setStrokeWidth(50);
        mPaintBackground.setAlpha(animatedValueAlpha);
        mPaintBackground.setAntiAlias(true);//消锯齿

        Paint mPaintMovingColor = new Paint();


        canvas.drawPath(path, mPaintBackground);              // 绘制Path
        canvas.save();
        LinearGradient backGradient = new LinearGradient(0, 0, lengthOfColor, 25, colors, position, Shader.TileMode.CLAMP);

        mPaintMovingColor.setShader(backGradient);
//        mPaintMovingColor.setAlpha(animatedValueAlpha);  //移动颜色块透明度变化

        if (animatedValue <= width) {
            canvas.translate(animatedValue, 0);

        } else if (width < animatedValue && animatedValue <= height + width) {
            canvas.translate(width, 0);
            canvas.rotate(90);

            canvas.translate(animatedValue - width, 0);

        } else if (height + width < animatedValue && animatedValue <= height + 2 * width) {
            canvas.translate(width, 0);
            canvas.rotate(90);

            canvas.translate(height, 0);
            canvas.rotate(90);
            canvas.translate(animatedValue - width - height, 0);

        } else if (height + 2 * width < animatedValue && animatedValue < 2 * height + 2 * width) {

            canvas.rotate(-90);
            canvas.translate(animatedValue - 2 * width - 2 * height, 0);

        }
        canvas.drawRect(0, 0, lengthOfColor, 25, mPaintMovingColor);
        canvas.restore();


    }

    public void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        Log.i(TAG, "construct,width:" + width + ", height:" + height);


        path = new Path();                     // 创建Path

        path.lineTo(width, 0);                      // lineTo
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, 0);
        path.close();
        //路径测量类
        PathMeasure mPathMeasure = new PathMeasure();
        //测量路径
        mPathMeasure.setPath(path, true);

        //获取被测量路径的总长度
        mLength = mPathMeasure.getLength();


        ValueAnimator animator = ValueAnimator.ofFloat(0, mLength);
        animator.setDuration(mTimeMoving).setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
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
