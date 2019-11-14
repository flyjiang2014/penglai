package com.penglai.haima.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;


/**
 * Created by ${flyjiang} on 2018/10/10.
 */
public class CommonOperateDialog extends BaseDialogView {

    OperateListener operateListener;
    private Button cancel;
    private Button confirm;
    private TextView tvContent;

    public CommonOperateDialog(BaseActivity activity) {
        super(activity);
        init(R.layout.dialog_single_operate);
    }

    public CommonOperateDialog(BaseActivity activity, OperateListener operateListener) {
        super(activity);
        this.operateListener = operateListener;
        init(R.layout.dialog_single_operate);
    }


    public CommonOperateDialog(BaseActivity activity, OperateListener operateListener, String tag) {
        super(activity);
        this.tag = tag;
        this.operateListener = operateListener;
        init(R.layout.dialog_single_operate);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);
        tvContent = findViewById(R.id.tvContent);
        setCancelable(false);
    }

    @Override
    public void initViewData() {
    }

    @Override
    public void initViewListener() {
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                operateListener.sure();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OperateListener {
        void sure();
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public void setContentText(String contentText) {
        tvContent.setText(contentText);
    }
}
