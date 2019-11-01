package com.penglai.haima.dialog;

import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.BaseView;

/**
 * dialog
 */

public abstract class BaseDialogView extends BaseView {

    private SuccessCallback successCallback;
    protected Dialog dialog;

    public BaseDialogView(BaseActivity activity) {
        super(activity);
        dialog = new Dialog(getActivity(), R.style.MyDialog);
        dialog.setContentView(this);
        dialog.setCancelable(true);
    }

    public BaseDialogView(BaseActivity activity, int style) {
        super(activity);
        dialog = new Dialog(getActivity(), style);
        dialog.setContentView(this);
        dialog.setCancelable(false);
    }

    public void setSuccessCallback(SuccessCallback callback) {
        this.successCallback = callback;
    }

    public void invokeSuccessCallback(Object result) {
        if (null != successCallback) {
            successCallback.invoke(result);
        }
    }

    public void setDialog() {
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    public void show() {
        try {
            if (!(getActivity()).isFinishing()) {
                dialog.show();
            }
        } catch (Exception e) {

        }
    }

    public void dismiss() {
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    public void isDismissFaild() {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(false);
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public interface SuccessCallback {
        void invoke(Object result);
    }
}
