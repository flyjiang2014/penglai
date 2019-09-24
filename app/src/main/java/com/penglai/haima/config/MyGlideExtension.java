package com.penglai.haima.config;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.penglai.haima.R;
/**
 * Created by ${flyjiang} on 2018/4/26.
 * 文件说明：更改这个类后需  Build -> Rebuild Project
 */
    @GlideExtension
    public class MyGlideExtension {
        private MyGlideExtension() {
        }
        @GlideOption
        public static void defaultOptions(RequestOptions options) { //更改这个类后需  Build -> Rebuild Project
            options.placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher); //设置全局默认属性
        }
    }
