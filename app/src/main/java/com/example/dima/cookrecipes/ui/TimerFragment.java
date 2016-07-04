package com.example.dima.cookrecipes.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dima.cookrecipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dima on 02.07.16.
 */
public class TimerFragment extends Fragment implements Animation.AnimationListener {

    @BindView(R.id.timer_fragment_imageView_timer)
    ImageView mTimerImage;
    @BindView(R.id.timer_fragment_textView_time)
    TextView mTextView;
    @BindView(R.id.timer_fragment_button_start)
    ImageButton mStartButton;

    MyCountDownTimer mTimer;
    Context mContext;

    int img_w, img_h;
    float t_angle, d_angle;
    int t_pos;
    int draw_position = 0;
    final float rotate_coef = 300f;
    float rotateAngle;
    RotateAnimation anim;

    Runnable updateUI;
    Matrix mt;
    Bitmap bmp_record_original, bmp_record_rotated;
    Canvas canvas;


    int curTime;
    int maxTime;

    boolean touching = false;
    boolean timerWork;
    boolean isInited;

    private MediaPlayer mediaPlayer;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_fragment, null);
        mContext = container.getContext();
        ButterKnife.bind(this, view);


        if (isInited == false)
            initView();
        else
            restoreView();


        updateUI = new Runnable() {
            @Override
            public void run() {

                // update timer
                float angle = (float) draw_position / rotate_coef;//180f/(float)Math.PI*
                rotateAngle = angle;

                mt.setRotate(angle, img_w / 2, img_w / 2);

                bmp_record_rotated.eraseColor(Color.TRANSPARENT);
                canvas.drawBitmap(bmp_record_original, mt, new Paint());

                mTimerImage.setImageBitmap(bmp_record_rotated);


                if (angle < 0) {

                    while (angle < 0)
                        angle += 360;
                    //    Log.d("onTouch", "update angle = " + angle);
                }

                //  mTimerImage.setRotation(angle);

                int timeVal = (int) ((angle / 360) * 3600);
                int sec = timeVal % 60;
                int min = (timeVal - sec) / 60;

                String minStr = "" + min;
                if (min < 10)
                    minStr = "0" + minStr;

                String secStr = "" + sec;
                if (sec < 10)
                    secStr = "0" + secStr;

                String timeStr = minStr + ":" + secStr;
                mTextView.setText(timeStr);

                curTime = timeVal;
                maxTime = curTime;
            }
        };

        mTimerImage.post(new Runnable() {
            @Override
            public void run() {
                img_w = mTimerImage.getWidth();
                img_h = mTimerImage.getHeight();

                bmp_record_original = BitmapFactory.decodeResource(getResources(), R.drawable.wheel);
                bmp_record_original = Bitmap.createScaledBitmap(bmp_record_original, img_w, img_w, true);

                bmp_record_rotated = Bitmap.createBitmap(img_w, img_w, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bmp_record_rotated);

                mTimerImage.setImageBitmap(bmp_record_original);
            }
        });

        mTimerImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float tx = event.getX();
                float ty = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (timerWork)
                            return false;
                        touching = true;
                        t_angle = (float) Math.atan2((ty - img_w / 2), (tx - img_w / 2)); //((ty - img_h/2)/(tx - img_h/2));//Math.abs//Math.signum(tx - img_h/2)*
                        //   Log.d("onTouch", "touch angle = " + t_angle);
                        t_pos = draw_position;


                        return true;

                    case MotionEvent.ACTION_MOVE:

                        d_angle = (float) Math.atan2((ty - img_w / 2), (tx - img_w / 2)) - t_angle;
                        // Log.d("onTouch", "update angle = "+d_angle);
                        draw_position = t_pos + (int) (rotate_coef * d_angle * 180f / Math.PI);

                        getActivity().runOnUiThread(updateUI);
                        return true;

                    case MotionEvent.ACTION_UP:
                        touching = false;
                        return true;

                    default:
                        return true;
                }
            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerWork == true) {
                    timerWork = false;
                    draw_position = 0;

                    mStartButton.setImageResource(R.drawable.start_btn);
                    mTimerImage.clearAnimation();

                    if (mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                    }
                    mTextView.setText("00:00");
                    if (mediaPlayer != null)
                        mediaPlayer.stop();

                } else {

                    if (curTime > 0) {
                        timerWork = true;
                        mStartButton.setImageResource(R.drawable.stop_btn);

                        mTimerImage.setImageBitmap(bmp_record_original);
                        mTimerImage.setRotation(0);


                        // anim = new RotateAnimation(0, -rotateAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        anim = new RotateAnimation(rotateAngle, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        anim.setDuration(curTime * 1000);
                        //anim.setDuration(5 * 1000);
                        anim.setInterpolator(new LinearInterpolator());
                        anim.setAnimationListener(TimerFragment.this);
                        mTimerImage.startAnimation(anim);
                        mTimer = new MyCountDownTimer(curTime * 1000, 1000);
                        mTimer.start();
                    }

                }
            }
        });

        return view;
    }

    public void initView() {
        rotateAngle = 0;
        mTextView.setText("00:00");

        img_w = mTimerImage.getWidth();
        img_h = mTimerImage.getHeight();
        mt = new Matrix();

        timerWork = false;
        isInited = true;
    }

    public void restoreView() {
        mTextView.setText("");

        if (timerWork) {
            mStartButton.setImageResource(R.drawable.stop_btn);
            float angle = (float) curTime / (float) maxTime;
            angle *= rotateAngle;
            Log.d("Timer Fragment", "Restore angle= " + angle);

            mTimerImage.setImageBitmap(bmp_record_original);
            mTimerImage.setRotation(0);


            // anim = new RotateAnimation(0, -rotateAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim = new RotateAnimation(angle, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(curTime * 1000);
            //anim.setDuration(5 * 1000);
            anim.setInterpolator(new LinearInterpolator());
            anim.setAnimationListener(TimerFragment.this);
            mTimerImage.startAnimation(anim);


        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        rotateAngle = 0;

        mTimerImage.setImageBitmap(bmp_record_original);
        mTimerImage.setRotation(0);


    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {


            mTimer.cancel();
            mTimer = null;
            mTextView.setText("00:00");
            mediaPlayer = MediaPlayer.create(mContext,
                    R.raw.alarm);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (timerWork == false)
                        return;
                    timerWork = false;
                    draw_position = 0;
                    mStartButton.setImageResource(R.drawable.start_btn);
                }
            });

            mediaPlayer.start();
        }

        @Override
        public void onTick(long millisUntilFinished) {

            curTime = (int) (millisUntilFinished / 1000);

            Log.d("Timer Fragment", "cur time= " + curTime);

            String minStr = "" + (millisUntilFinished / 1000) / 60;
            String secStr = "" + (millisUntilFinished / 1000) % 60;


            if ((millisUntilFinished / 1000) / 60 < 10)
                minStr = "0" + (millisUntilFinished / 1000) / 60;
            if ((millisUntilFinished / 1000) % 60 < 10)
                secStr = "0" + (millisUntilFinished / 1000) % 60;

            String timeStr = minStr + ":" + secStr;
            mTextView.setText(timeStr);
        }
    }
}
