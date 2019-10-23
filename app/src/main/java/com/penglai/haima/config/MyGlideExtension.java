package com.penglai.haima.config;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.BaseRequestOptions;
import com.penglai.haima.R;
import io.reactivex.annotations.NonNull;

/**
 * Created by ${flyjiang} on 2018/4/26.
 * 文件说明：更改这个类后需  Build -> Rebuild Project
 * https://muyangmin.github.io/glide-docs-cn/doc/generatedapi.html
 * 定义一个在 应用模块中频繁使用的默认选项配置
 */
@GlideExtension
public class MyGlideExtension {
    private MyGlideExtension() {
    }

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> defaultOptions(BaseRequestOptions<?> options) {
        return options.placeholder(R.color.text_grey666).error(R.color.text_grey666); //设置全局默认属性;
    }
}
