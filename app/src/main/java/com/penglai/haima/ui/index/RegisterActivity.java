package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.penglai.haima.R;

public class RegisterActivity extends AppCompatActivity {
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        timeCount = new TimeCount(60000, 1000);// 构造CountDownTimer对象
    }


    /**
     * 倒计时控件
     */
    class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
//            btnGetCode.setText("重新获取");
//            btnGetCode.setBackgroundResource(R.drawable.frame_solid_orange);
//            btnGetCode.setBackgroundResource(R.drawable.frame_solid_grey);
//            btnGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
//            btnGetCode.setClickable(false);
//            btnGetCode.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }
}
