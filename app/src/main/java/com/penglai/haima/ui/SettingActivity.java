package com.penglai.haima.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.utils.SharepreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.penglai.haima.utils.ActivityManager.activityStack;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("设置");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.ll_about_us, R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_about_us:
                break;
            case R.id.tv_login_out:
                SharepreferenceUtil.removeKeyValue(Constants.IS_GOLIN);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                for (int i = 0, size = activityStack.size(); i < size; i++) {  //关闭登录页面外的其他页面
                    if (null != activityStack.get(i) && !(activityStack.get(i) instanceof LoginActivity)) {
                        activityStack.get(i).finish();
                    }
                }
                break;
        }
    }
}
