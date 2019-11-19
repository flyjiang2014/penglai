package com.penglai.haima.widget;

import android.content.Context;

import com.bumptech.glide.Glide;

/**
 * Created by  on 2019/11/19.
 * 文件说明：
 */
public class GlideImageLoaderLocal extends ImageLoaderLocal {

    @Override
    public void displayImage(Context context, Object path, RoundImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
    }
}
