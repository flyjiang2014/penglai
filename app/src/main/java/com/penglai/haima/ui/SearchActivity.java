package com.penglai.haima.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.utils.KeyboardUtil;
import com.penglai.haima.utils.PhoneUtil;
import com.penglai.haima.utils.SharepreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.title_layout_left)
    RelativeLayout titleLayoutLeft;
    @BindView(R.id.title_layout_right)
    RelativeLayout titleLayoutRight;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.et_key_word)
    EditText etKeyWord;
    @BindView(R.id.view)
    View view_top;
    private List<String> historys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardUtil.openKeyboard(mContext);
    }

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        historys.addAll(SharepreferenceUtil.getListData(Constants.SEARCH_KEY_WORDS));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //
        etKeyWord.setOnEditorActionListener(mEditorActionListener);
        ViewGroup.LayoutParams params = view_top.getLayoutParams();
        //获取当前控件的布局对象
        params.height = PhoneUtil.getStatusHeight(mContext) + 5;//设置当前控件布局的高度
        view_top.setLayoutParams(params);
    }

    @OnClick({R.id.title_layout_left, R.id.title_layout_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_layout_left:
                finish();
                break;
            case R.id.title_layout_right:
                searchAction();
                break;
        }
    }

    /**
     * 键盘搜索的监听
     */
    private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_SEARCH:
                    searchAction();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private void searchAction() {
        String content = etKeyWord.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            historys.add(0, content);
            SharepreferenceUtil.saveListData(Constants.SEARCH_KEY_WORDS, historys);
            EventBus.getDefault().post(new EventBean(EventBean.SEARCH_ACTION, content));
            KeyboardUtil.closeKeyboard(mContext);
            finish();
        } else {
            showToast("请输入搜索内容");
        }
    }
}
