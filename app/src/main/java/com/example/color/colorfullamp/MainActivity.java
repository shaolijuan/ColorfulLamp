package com.example.color.colorfullamp;

/**
 * Created by 86183 on 2019/10/13.
 */



        import android.app.Activity;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
//import android.view.WindowManagerPolicyConstants.PointerEventListener;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.TextView;



public class MainActivity extends Activity {
    private TextView mTextView;
    private boolean mTextDisplay = false;
    private String TAG = "MainActivity";
    private GestureDetector detector;

//    private  PointerEventListener mPointerEventListener;
//    public SystemGesturesPointerEventListener  mSystemGestures ;
//private WindowManagerPolicy managerPolicy;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

          addNightView();


        mTextView = (TextView) findViewById(R.id.textview);
        Button mButtom = findViewById(R.id.button);
        mButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextDisplay == false) {
                    mTextView.setText("hhhhhhhh");
                    mTextDisplay = true;
                } else if (mTextDisplay == true) {
                    mTextView.setText("");
                    mTextDisplay = false;
                }
            }
        });

    }



//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        final float x = event.getX();
//        final float y = event.getY();
//        final int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_CANCEL:
//                Log.w(TAG, "onScroll:"+"ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.w(TAG, "onScroll:"+"ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.w(TAG, "onScroll:"+"ACTION_UP");
//                break;
//            case MotionEvent.ACTION_DOWN:
//                Log.w(TAG, "onScroll:"+"ACTION_DOWN");
//                break;
//        }
//
//
//        return super.dispatchTouchEvent(event);
//    }

//    /////增加动画图层
//    protected void addNightView() {
//
//
//        LayoutInflater inflater3 = LayoutInflater.from(this);
//        View lampView = (View) inflater3.inflate(R.layout.layout_lamp1, null);
//        ((ViewGroup) getWindow().getDecorView()).addView(lampView);
//        LampView mLampView = (LampView) lampView.findViewById(R.id.lamp_view);
//        mLampView.init();
//
//    }

    /////增加动画图层
    protected void addNightView() {


        LayoutInflater inflater = LayoutInflater.from(this);
        View lampView = (View) inflater.inflate(R.layout.layout_lamp1, null);
        ((ViewGroup) getWindow().getDecorView()).addView(lampView);
        LampView2 mLampView = (LampView2) lampView.findViewById(R.id.lamp_view);
        mLampView.init();

    }





}