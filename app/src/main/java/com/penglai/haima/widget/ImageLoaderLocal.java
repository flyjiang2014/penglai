package com.penglai.haima.widget;

import android.content.Context;

import com.youth.banner.loader.ImageLoaderInterface;

/**
 * Created by  on 2019/11/19.
 * 文件说明：
 */
public abstract class ImageLoaderLocal implements ImageLoaderInterface<RoundImageView> {
    RoundImageView roundImageView;

    @Override
    public RoundImageView createImageView(Context context) {

        roundImageView = new RoundImageView(context);
        return roundImageView;
    }
}
