package com.penglai.haima.callback;

import android.app.Activity;
import android.app.Dialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.okgomodel.SimpleResponse;
import com.penglai.haima.utils.Convert;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.request.base.Request;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

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

    @Override
    public T convertResponse(Response response) throws Exception {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        Type rawType = ((ParameterizedType) type).getRawType();
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        if (rawType == Void.class) {
            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            response.close();
            return (T) simpleResponse.toCommonReturnData();
        } else if (rawType == CommonReturnData.class) {
            CommonReturnData commonReturnData =  Convert.fromJson(jsonReader, type);
            response.close();
            return (T) commonReturnData;
        }
        else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }
}
