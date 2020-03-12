package com.penglai.haima.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;

import java.util.List;

/**
 * Created by  on 2020/3/12.
 * 文件说明：
 */
public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private boolean delete_choose_statue;

    public SearchHistoryAdapter(@Nullable List<String> data) {
        super(R.layout.item_search_word, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        TextView tv_word = helper.getView(R.id.tv_word);
        ImageView img_delete = helper.getView(R.id.img_delete);
        View line = helper.getView(R.id.line);
        tv_word.setText(item);
        img_delete.setVisibility(delete_choose_statue ? View.VISIBLE : View.GONE);
        line.setVisibility(helper.getLayoutPosition() % 2 == 0 ? View.VISIBLE : View.GONE);
    }

    public void setDelete_choose_statue(boolean delete_choose_statue) {
        this.delete_choose_statue = delete_choose_statue;
        notifyDataSetChanged();
    }

    public boolean isDelete_choose_statue() {
        return delete_choose_statue;
    }
}
