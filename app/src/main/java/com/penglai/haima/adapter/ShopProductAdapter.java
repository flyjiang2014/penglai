package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ShopProductBean;
import com.penglai.haima.config.GlideApp;
import com.penglai.haima.ui.shop.ShopDetailsActivity;

import java.util.List;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class ShopProductAdapter extends BaseQuickAdapter<ShopProductBean, BaseViewHolder> {
    ShopDetailsActivity activity;

    public ShopProductAdapter(ShopDetailsActivity activity, @Nullable List<ShopProductBean> data) {
        super(R.layout.item_shop_product_layout, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopProductBean item) {
        ImageView img_pic = helper.getView(R.id.img_pic);
        final ImageView iv_add = helper.getView(R.id.iv_add);
        final ImageView iv_remove = helper.getView(R.id.iv_remove);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_model = helper.getView(R.id.tv_model);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_number = helper.getView(R.id.tv_number);
        final TextView tv_count = helper.getView(R.id.tv_count);

        tv_price.setText("￥" + item.getPrice());
        tv_content.setText(item.getContent());
        tv_title.setText(item.getTitle());
        tv_model.setText("型号：" + item.getModel());
        tv_number.setText("库存：" + item.getNumber());
        GlideApp.with(mContext).load(Constants.URL_FOR_PIC + item.getImage_name() + Constants.PIC_JPG).defaultOptions().into(img_pic);

        if (item != null) {
            //默认进来数量
            if (item.getChoose_number() < 1) {
                tv_count.setVisibility(View.INVISIBLE);
                iv_remove.setVisibility(View.INVISIBLE);

            } else {
                tv_count.setVisibility(View.VISIBLE);
                iv_remove.setVisibility(View.VISIBLE);
                tv_count.setText(String.valueOf(item.getChoose_number()));

            }
        } else {
            tv_count.setVisibility(View.INVISIBLE);
            iv_remove.setVisibility(View.INVISIBLE);
        }

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = activity.getSelectedItemCountById(item.getId());
                if (count < 1) {
                    iv_remove.setAnimation(getShowAnimation());
                    iv_remove.setVisibility(View.VISIBLE);
                    tv_count.setVisibility(View.VISIBLE);
                }

                activity.handlerCarNum(1, item, true);

                int[] loc = new int[2];
                iv_add.getLocationInWindow(loc);
                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ImageView ball = new ImageView(mContext);
                ball.setImageResource(R.drawable.number);
                activity.setAnim(ball, startLocation);// 开始执行动画
            }
        });
        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = activity.getSelectedItemCountById(item.getId());
                if (count < 2) {
                    iv_remove.setAnimation(getHiddenAnimation());
                    iv_remove.setVisibility(View.GONE);
                    tv_count.setVisibility(View.GONE);
                }
                activity.handlerCarNum(0, item, true);

            }
        });
    }


    //显示减号的动画
    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    //隐藏减号的动画
    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
}
