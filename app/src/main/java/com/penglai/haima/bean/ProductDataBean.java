package com.penglai.haima.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2020/3/9.
 * 文件说明：
 */
public class ProductDataBean {
    private int currentPage;
    private String end;
    private List<ProductBean> data = new ArrayList<>();

    public int getCurrentPage() {
        return currentPage;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<ProductBean> getData() {
        return data;
    }

    public void setData(List<ProductBean> data) {
        this.data = data;
    }
}
