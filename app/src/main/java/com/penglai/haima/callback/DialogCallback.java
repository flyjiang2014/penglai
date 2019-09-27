package com.penglai.haima.callback;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.penglai.haima.base.BaseActivity;

/**
 * 描    述：对于网络请求是否需要弹出加载框
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {

    public DialogCallback(Activity activity) {
        super(activity);
    }

    public DialogCallback(Activity mActivity, boolean showStatue) {
        super(mActivity, showStatue);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (mActivity != null) {
            ((BaseActivity) mActivity).getDialog().show();
        }
        //网络请求前显示对话框
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        if (mActivity != null) {
            ((BaseActivity) mActivity).getDialog().dismiss();//请求框架可能出现 OnError回调后,OnFinish 不回调情况，这边补充关闭 dialog.
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        //网络请求结束后关闭对话框
        if (mActivity != null) {
            ((BaseActivity) mActivity).getDialog().dismiss();
        }
    }
}
