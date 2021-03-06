package com.penglai.haima.adapter;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ProductBean;
import com.penglai.haima.config.GlideApp;
import com.penglai.haima.ui.index.ProductIndexFragment;
import com.penglai.haima.utils.DensityUtil;

import java.util.List;

/**
 * Created by ${flyjiang} on 2019/10/17.
 * 文件说明：
 */
public class ProductAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {
    ProductIndexFragment fragment;

    public ProductAdapter(ProductIndexFragment fragment, List<ProductBean> data) {
        //   super(R.layout.item_product_layout, data);
        super(R.layout.item_online_product_layout, data);
        this.fragment = fragment;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProductBean item) {
        ImageView img_pic = helper.getView(R.id.img_pic);
        final ImageView iv_add = helper.getView(R.id.iv_add);
        final ImageView iv_remove = helper.getView(R.id.iv_remove);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_model = helper.getView(R.id.tv_model);
        TextView tv_price = helper.getView(R.id.tv_price);
        LinearLayout ll_whole = helper.getView(R.id.ll_whole);
        //  TextView tv_number = helper.getView(R.id.tv_number);
        final TextView tv_count = helper.getView(R.id.tv_count);

        tv_price.setText("￥" + item.getPrice());
        tv_title.setText(item.getTitle());
        tv_content.setText(item.getContent());
        tv_model.setText("型号：" + item.getModel());
        //  tv_number.setText("库存：" + item.getNumber());
        GlideApp.with(mContext).load(Constants.URL_FOR_PIC + item.getImage_name() + Constants.PIC_JPG).defaultOptions().into(img_pic);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (helper.getLayoutPosition() % 2 == 1) {
            lp.setMargins(DensityUtil.dp2px(mContext, 10), 0, 5, 0);
        } else {
            lp.setMargins(5, 0, DensityUtil.dp2px(mContext, 10), 0);
        }
        ll_whole.setLayoutParams(lp);
//        if (item != null) {
//            //默认进来数量
//            if (item.getAmount() < 1) {
//                tv_count.setVisibility(View.INVISIBLE);
//                iv_remove.setVisibility(View.INVISIBLE);
//
//            } else {
//                tv_count.setVisibility(View.VISIBLE);
//                iv_remove.setVisibility(View.VISIBLE);
//                tv_count.setText(String.valueOf(item.getAmount()));
//            }
//        } else {
//            tv_count.setVisibility(View.INVISIBLE);
//            iv_remove.setVisibility(View.INVISIBLE);
//        }
        tv_count.setVisibility(View.INVISIBLE);//删除显示功能
        iv_remove.setVisibility(View.INVISIBLE);//删除显示功能
        helper.addOnClickListener(R.id.iv_add);
//        iv_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = fragment.getSelectedItemCountById(item.getId());
//                if (count < 1) {
//                    iv_remove.setAnimation(getShowAnimation());
//                    iv_remove.setVisibility(View.VISIBLE);
//                    tv_count.setVisibility(View.VISIBLE);
//                }
//                fragment.handlerCarNum(1, item, true);
//                int[] loc = new int[2];
//                iv_add.getLocationInWindow(loc);
//                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
//                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
//                ImageView ball = new ImageView(mContext);
//                ball.setImageResource(R.drawable.number);
//                fragment.setAnim(ball, startLocation);// 开始执行动画
//            }
//        });

//        iv_remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = fragment.getSelectedItemCountById(item.getId());
//                if (count < 2) {
//                    iv_remove.setAnimation(getHiddenAnimation());
//                    iv_remove.setVisibility(View.GONE);
//                    tv_count.setVisibility(View.GONE);
//                }
//                fragment.handlerCarNum(0, item, true);
//
//            }
//        });
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
