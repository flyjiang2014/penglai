package com.penglai.haima.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.TradeBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ClickUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceCommentActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private String serviceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("评价");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_service_comment;
    }

    @Override
    public void init() {
        serviceId = getIntent().getStringExtra("serviceId");
        tvTitle.setText(getIntent().getStringExtra("title"));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvLevel.setText(getRatingLevel(rating));
            }
        });
    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (ClickUtil.isFastDoubleClick()) {
                    return;  //防止快速多次点击
                }
                doComment(ratingBar.getRating(), etContent.getText().toString().trim());
                break;
        }
    }


    /**
     * 获取评价等级
     *
     * @param rating
     * @return
     */
    public String getRatingLevel(float rating) {
        if (1.0 == rating) {
            return "非常差";

        } else if (2.0 == rating) {
            return "差";
        } else if (3.0 == rating) {
            return "一般";
        } else if (4.0 == rating) {
            return "好";
        } else if (5.0 == rating) {
            return "非常好";
        }
        return "";
    }

    /**
     * 评价操作
     *
     * @param score
     * @param notes
     */
    private void doComment(float score, String notes) {
        OkGo.<CommonReturnData<TradeBean>>post(Constants.BASE_URL + "score/insertProviderScore")
                .params("serviceId", serviceId)
                .params("score", (int) (score))
                .params("notes", notes)
                .execute(new DialogCallback<CommonReturnData<TradeBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<TradeBean> commonReturnData) {
                        showToast("点评成功");
                        EventBus.getDefault().post(new EventBean(EventBean.SERVICE_COMMENT_SUCCESS));
                        ServiceCommentActivity.this.finish();
                    }
                });
    }
}
