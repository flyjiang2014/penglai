package com.penglai.haima.callback;

import android.app.Activity;

import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.okgomodel.SimpleResponse;
import com.penglai.haima.utils.Convert;
import com.penglai.haima.utils.ToastUtil;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * -我的注释都已经写的不能再多了,不要再来问我怎么获取数据对象,怎么解析集合数据了,你只要会 gson ,就会解析
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {
     Activity mActivity;
    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        request.tag(mActivity.getClass().getSimpleName());
//        request.headers("header1", "HeaderValue1")
//                .params("params1", "ParamsValue1");
    }

    public JsonCallback() {
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

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Exception e = (Exception)response.getException();
        if (e instanceof ConnectException) {
            //网络异常，请求超时
            ToastUtil.showToast("当前未连接到网络");
        } else if (e instanceof UnknownHostException) {
            // ToastUtil.showToast(e.toString());
            ToastUtil.showToast("网络服务器连接失败");
        } else if (e instanceof SocketException) {
            //网络异常，读取数据超时
            ToastUtil.showToast(e.toString());
        } else if (e instanceof JsonSyntaxException) {
            ToastUtil.showToast(e.toString());
        } else if (e instanceof SocketTimeoutException) {
            ToastUtil.showToast(e.toString());
        }else if (e instanceof IllegalStateException) {
            ToastUtil.showToast(e.toString());
        }
    }
}