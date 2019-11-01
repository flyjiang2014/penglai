package com.penglai.haima.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

/**
 * Created by ${flyjiang}
 * 文件说明：显示message
 */
public class MessageShowDialog extends BaseDialogView {

    OperateListener operateListener;
    private Button cancel;
    private Button confirm;
    private TextView tvContent;

    public MessageShowDialog(BaseActivity activity) {
        super(activity);
        init(R.layout.dialog_message);
    }

    public MessageShowDialog(BaseActivity activity, OperateListener operateListener) {
        super(activity);
        this.operateListener = operateListener;
        init(R.layout.dialog_message);
    }


    public MessageShowDialog(BaseActivity activity, OperateListener operateListener, String tag) {
        super(activity);
        this.tag = tag;
        this.operateListener = operateListener;
        init(R.layout.dialog_message);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        confirm = findViewById(R.id.confirm);
        tvContent = findViewById(R.id.tvContent);
        setCancelable(false);
    }

    @Override
    public void initViewData() {
    }


    @Override
    public void initViewListener() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operateListener.sure();
            }
        });
    }

    public interface OperateListener {
        void sure();
    }

    public void setContentText(String contentText) {
        tvContent.setText(contentText);
    }
}
