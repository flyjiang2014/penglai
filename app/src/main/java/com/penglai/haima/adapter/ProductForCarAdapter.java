package com.penglai.haima.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.bean.ProductBean;
import com.penglai.haima.ui.index.ProductIndexFragment;
import com.penglai.haima.utils.StringUtil;

/**
 */

/***
 * 底部购物车
 */
public class ProductForCarAdapter extends BaseAdapter {

    ProductAdapter productAdapter;
    private ProductIndexFragment fragment;
    private SparseArray<ProductBean> dataList;

    public ProductForCarAdapter(ProductIndexFragment fragment, ProductAdapter productAdapter, SparseArray<ProductBean> dataList) {
        this.productAdapter = productAdapter;
        this.fragment = fragment;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.product_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            viewHolder.iv_add = (ImageView) view.findViewById(R.id.iv_add);
            viewHolder.iv_remove = (ImageView) view.findViewById(R.id.iv_remove);
            viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_count);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        StringUtil.filtNull(viewHolder.tv_name, dataList.valueAt(position).getTitle());//商品名称
        StringUtil.filtNull(viewHolder.tv_price, "￥" + dataList.valueAt(position).getPrice());//商品价格
        viewHolder.tv_count.setText(String.valueOf(dataList.valueAt(position).getChoose_number()));//商品数量
        viewHolder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.updateShopCarFromBasic(true, dataList.valueAt(position), null);
//                fragment.handlerCarNum(1, dataList.valueAt(position), true);
//                productAdapter.notifyDataSetChanged();
            }
        });
        viewHolder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.updateShopCarFromBasic(false, dataList.valueAt(position), null);
//                fragment.handlerCarNum(0, dataList.valueAt(position), true);
//                productAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    class ViewHolder {
        TextView tv_price;
        TextView tv_name;
        ImageView iv_add, iv_remove;
        TextView tv_count;
    }
}