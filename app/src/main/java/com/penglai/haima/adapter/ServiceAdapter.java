package com.penglai.haima.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.penglai.haima.R;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ServiceBean;
import com.penglai.haima.config.GlideApp;

import java.util.List;


/**
 * Created by  on 2019/10/30.
 * 文件说明：
 */
public class ServiceAdapter extends MultipleItemRvAdapter<ServiceBean, BaseViewHolder> {

    public static final int SERVICE_PERSON = 100;
    public static final int SERVICE_ORG = 200;

    public ServiceAdapter(@Nullable List<ServiceBean> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(ServiceBean serviceBean) {
        if ("0".equals(serviceBean.getOrgtype())) {
            return SERVICE_PERSON;
        } else if ("1".equals(serviceBean.getOrgtype())) {
            return SERVICE_ORG;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new ServicePersonProvider());
        mProviderDelegate.registerProvider(new ServiceORGProvider());
    }

    class ServicePersonProvider extends BaseItemProvider<ServiceBean, BaseViewHolder> {
        @Override
        public int viewType() {
            return SERVICE_PERSON;
        }

        @Override
        public int layout() {
            return R.layout.item_service_person_layout;
        }

        @Override
        public void convert(BaseViewHolder helper, ServiceBean data, int position) {
            helper.setText(R.id.tv_name, data.getName());
            helper.setText(R.id.tv_age, data.getAge() + "岁");
            String summary = data.getInd_summary().replace("/br", "\n");
            helper.setText(R.id.tv_summary, summary);
            helper.setText(R.id.tv_score, data.getScore() + "%");
            ImageView img_pic = helper.getView(R.id.img_pic);
            GlideApp.with(mContext).load(Constants.URL_FOR_PIC2 + data.getCover_image() + Constants.PIC_JPG).defaultOptions().into(img_pic);

        }
    }

    class ServiceORGProvider extends BaseItemProvider<ServiceBean, BaseViewHolder> {
        @Override
        public int viewType() {
            return SERVICE_ORG;
        }

        @Override
        public int layout() {
            return R.layout.item_service_org_layout;
        }

        @Override
        public void convert(BaseViewHolder helper, ServiceBean data, int position) {
            helper.setText(R.id.tv_title, data.getTitle());
            String summary = data.getOrg_summary().replace("/br", "\n");
            helper.setText(R.id.tv_summary, summary);
            helper.setText(R.id.tv_price, ("￥" + data.getOrg_price()));
            helper.setText(R.id.tv_left, data.getLeft_number());
            helper.setText(R.id.tv_detail, data.getLeft_info() + ":");
            ImageView img_pic = helper.getView(R.id.img_pic);
            GlideApp.with(mContext).load(Constants.URL_FOR_PIC2 + data.getCover_image() + Constants.PIC_JPG).defaultOptions().into(img_pic);
        }
    }

}
