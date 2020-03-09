package com.penglai.haima.dialog;

import com.azhon.appupdate.dialog.NumberProgressBar;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class UpdateShowingDialog extends BaseDialogView {
    private NumberProgressBar numberProgressBar;

    public UpdateShowingDialog(BaseActivity activity) {
        super(activity);
        init(R.layout.dialog_update_showing);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initViewData() {
        numberProgressBar = findViewById(R.id.number_progress_bar);
    }

    @Override
    public void initViewListener() {
    }

    public void setProcess(int curr) {
        numberProgressBar.setMax(100);
        numberProgressBar.setProgress(curr);
    }
}
