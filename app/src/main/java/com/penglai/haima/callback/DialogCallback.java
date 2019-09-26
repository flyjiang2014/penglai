package com.penglai.haima.callback;

import android.app.Activity;
import android.app.Dialog;

import com.lzy.okgo.request.base.Request;

/**
 * ================================================
 * 描    述：对于网络请求是否需要弹出进度对话框
 * 修订历史：
 * ================================================
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {
    private Dialog dialog;
  /*  private Activity mActivity;*/
  /*  private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }*/

  public DialogCallback(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        //网络请求前显示对话框
        /*if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }*/
    }

    @Override
    public void onFinish() {
        super.onFinish();
        //网络请求结束后关闭对话框
       /* if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }*/
    }
}
