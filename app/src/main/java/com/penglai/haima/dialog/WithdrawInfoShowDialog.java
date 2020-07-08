package com.penglai.haima.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.bean.WithdrawDetailBean;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class WithdrawInfoShowDialog extends BaseDialogView {
    TextView tv_name, tv_process, tv_account;
    ImageView img_close;
    WithdrawDetailBean withdrawDetailBean;


    public WithdrawInfoShowDialog(BaseActivity activity, WithdrawDetailBean withdrawDetailBean) {
        super(activity);
        this.withdrawDetailBean = withdrawDetailBean;
        init(R.layout.dialog_withdraw_info);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        tv_name = findViewById(R.id.tv_name);
        tv_process = findViewById(R.id.tv_process);
        tv_account = findViewById(R.id.tv_account);
        img_close = findViewById(R.id.img_close);
    }

    @Override
    public void initViewData() {
        //0：提交成功，1：已受理，2：提现成功
        switch (withdrawDetailBean.getProgress()) {
            case "0":
                tv_process.setText("提交成功");
                break;
            case "1":
                tv_process.setText("已受理");
                break;
            case "2":
                tv_process.setText("提现成功");
                break;
        }
        tv_account.setText("账号:" + withdrawDetailBean.getAccount());
        tv_name.setText("姓名:" + withdrawDetailBean.getName());
    }

    @Override
    public void initViewListener() {
        img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
