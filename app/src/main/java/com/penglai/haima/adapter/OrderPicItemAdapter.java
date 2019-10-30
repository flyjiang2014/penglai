package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.config.GlideApp;
import com.penglai.haima.utils.PhoneUtil;
import com.penglai.haima.utils.ScreenUtil;

import java.util.List;

/**
 * Created by  on 2019/10/30.
 * 文件说明：
 */
public class OrderPicItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public OrderPicItemAdapter(@Nullable List<String> data) {
        super(R.layout.item_pic_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView img_pic = helper.getView(R.id.img_pic);
        ViewGroup.LayoutParams params = img_pic.getLayoutParams();
        params.width = (PhoneUtil.getScreenWidth(mContext) - ScreenUtil.dip2px(mContext, 30)) / 4; //减去padding值
        params.height = (PhoneUtil.getScreenWidth(mContext) - ScreenUtil.dip2px(mContext, 30)) / 4;
        img_pic.setLayoutParams(params);
        GlideApp.with(mContext).load(item).defaultOptions().into(img_pic);
    }
}
