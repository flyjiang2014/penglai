package com.penglai.haima.callback;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.request.base.Request;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.okgomodel.SimpleResponse;
import com.penglai.haima.utils.Convert;
import com.penglai.haima.utils.ToastUtil;
import com.penglai.haima.widget.loading.LoadingLayout;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.Response;

/**
 * 网络请求callBack处理
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {
     Activity mActivity;
    boolean  showStatue; //请求是否响应loading状态,用于展示noNetView, loadingView，emptyView,errorView
    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        List<Cookie> allCookie = cookieStore.getAllCookie();
        Log.e("jiang", allCookie.size() + "");
        if (allCookie.size() > 0) {
            request.headers("Cookie", allCookie.get(0).toString());
        }
        request.tag(mActivity.getClass().getSimpleName());
    }

    public JsonCallback(Activity mActivity, boolean showStatue) {
        this.mActivity = mActivity;
        this.showStatue = showStatue;
    }

    public JsonCallback(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Exception {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        Log.e("jiang", response.header("sessionstatue") + "123");
        if (TextUtils.equals(response.header("sessionstatue"), "timeout")) {
            throw new IllegalStateException("cookie过期");
        }
        Log.e("jiang", "timeout");

        Type rawType = ((ParameterizedType) type).getRawType();
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        if (rawType == Void.class) {
            SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
            response.close();
            return (T) simpleResponse.toCommonReturnData();
        } else if (rawType == CommonReturnData.class) {
            final CommonReturnData commonReturnData =  Convert.fromJson(jsonReader, type);
            response.close();
            int statue= commonReturnData.getStatus();
            if(statue==1){
                return (T) commonReturnData;
            }else{
                throw new IllegalStateException("错误代码：" + commonReturnData.getErrorCode() + "，错误信息：" + commonReturnData.getMessage());
            }
        }
        else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        if (showStatue) {
            ((BaseActivity) mActivity).getmLoadingLayout().setStatus(LoadingLayout.Success);
        }
        onSuccess(response.body());
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Exception e = (Exception)response.getException();
        handleError(e);
    }

    /**
     * 部分Exception 处理
     * @param e
     */
    private void handleError(Exception e) {
        if (e instanceof ConnectException) {
            //网络异常，请求超时
            ToastUtil.showToast("当前未连接到网络");
            if (showStatue && ((BaseActivity) mActivity).getmLoadingLayout() != null) {
                ((BaseActivity) mActivity).getmLoadingLayout().setStatus(LoadingLayout.No_Network);
            }

        } else if (e instanceof UnknownHostException) {
            // ToastUtil.showToast(e.toString());
            ToastUtil.showToast("网络服务器连接失败");
            if (showStatue && ((BaseActivity) mActivity).getmLoadingLayout() != null) {
                ((BaseActivity) mActivity).getmLoadingLayout().setStatus(LoadingLayout.No_Network);
            }
        } else if (e instanceof SocketException) {
            //网络异常，读取数据超时
            ToastUtil.showToast(e.toString());
            if (showStatue && ((BaseActivity) mActivity).getmLoadingLayout() != null) {
                ((BaseActivity) mActivity).getmLoadingLayout().setStatus(LoadingLayout.No_Network);
            }
        } else if (e instanceof JsonSyntaxException) {
            ToastUtil.showToast(e.toString());
        } else if (e instanceof SocketTimeoutException) {
            ToastUtil.showToast(e.toString());
            if (showStatue && ((BaseActivity) mActivity).getmLoadingLayout() != null) {
                ((BaseActivity) mActivity).getmLoadingLayout().setStatus(LoadingLayout.No_Network);
            }
        }else if (e instanceof IllegalStateException) {
            ToastUtil.showToast(e.getMessage());
        }
    }
    public abstract void onSuccess(T t);
}