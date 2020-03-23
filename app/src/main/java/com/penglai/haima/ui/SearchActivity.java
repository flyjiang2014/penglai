package com.penglai.haima.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.penglai.haima.R;
import com.penglai.haima.adapter.SearchHistoryAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.dialog.CommonOperateDialog;
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
    @BindView(R.id.tv_history_show)
    TextView tvHistoryShow;
    @BindView(R.id.tv_delete_all)
    TextView tvDeleteAll;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> historys = new ArrayList<>();
    SearchHistoryAdapter searchHistoryAdapter;
    CommonOperateDialog commonOperateDialog;

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
        searchHistoryAdapter = new SearchHistoryAdapter(historys);
        List list = SharepreferenceUtil.getListData(Constants.SEARCH_KEY_WORDS);
        if (list.isEmpty()) {
            tvHistoryShow.setText("暂无搜索记录");
            imgDelete.setVisibility(View.GONE);
        } else {
            historys.addAll(list);
            searchHistoryAdapter.notifyDataSetChanged();
            imgDelete.setVisibility(View.VISIBLE);
            tvHistoryShow.setText("历史记录");
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //
        etKeyWord.setOnEditorActionListener(mEditorActionListener);
        ViewGroup.LayoutParams params = view_top.getLayoutParams();
        params.height = PhoneUtil.getStatusHeight(mContext) + 5;//设置当前控件布局的高度
        view_top.setLayoutParams(params);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        // recyclerView.addItemDecoration(new Gr(mContext, R.drawable.divider_drawable));
        recyclerView.setAdapter(searchHistoryAdapter);
        searchHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (searchHistoryAdapter.isDelete_choose_statue()) {
                    searchHistoryAdapter.remove(position);
                    SharepreferenceUtil.saveListData(Constants.SEARCH_KEY_WORDS, historys);
                    if (historys.isEmpty()) {
                        tvHistoryShow.setText("暂无搜索记录");
                        imgDelete.setVisibility(View.GONE);
                        tvDeleteAll.setVisibility(View.GONE);
                        tvFinish.setVisibility(View.GONE);
                    }
                } else {
                    searchAction(historys.get(position));
                }
            }
        });
    }

    @OnClick({R.id.title_layout_left, R.id.title_layout_right, R.id.tv_delete_all, R.id.tv_finish, R.id.img_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_layout_left:
                finish();
                break;
            case R.id.title_layout_right:
                searchAction(etKeyWord.getText().toString().trim());
                break;
            case R.id.tv_delete_all:
                commonOperateDialog = new CommonOperateDialog(this, new CommonOperateDialog.OperateListener() {
                    @Override
                    public void sure() {
                        historys.clear();
                        tvHistoryShow.setText("暂无搜索记录");
                        imgDelete.setVisibility(View.GONE);
                        tvDeleteAll.setVisibility(View.GONE);
                        tvFinish.setVisibility(View.GONE);
                        SharepreferenceUtil.saveListData(Constants.SEARCH_KEY_WORDS, historys);
                        commonOperateDialog.dismiss();
                    }
                });
                commonOperateDialog.setContentText("确认删除全部历史记录？");
                commonOperateDialog.show();
                break;
            case R.id.tv_finish:
                imgDelete.setVisibility(View.VISIBLE);
                tvDeleteAll.setVisibility(View.GONE);
                tvFinish.setVisibility(View.GONE);
                searchHistoryAdapter.setDelete_choose_statue(false);
                break;
            case R.id.img_delete:
                imgDelete.setVisibility(View.GONE);
                tvDeleteAll.setVisibility(View.VISIBLE);
                tvFinish.setVisibility(View.VISIBLE);
                searchHistoryAdapter.setDelete_choose_statue(true);
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
                    searchAction(etKeyWord.getText().toString().trim());
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private void searchAction(String content) {
        if (!TextUtils.isEmpty(content)) {
            historys.remove(content);
            if (historys.size() >= 20) {
                historys.remove(19);//大于二十个关键字，删除最后一个
            }
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
