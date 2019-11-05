package com.penglai.haima.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class TraceBean {
    private String kdState = "";
    private String company = "";
    private List<TraceFlowBean> traces = new ArrayList<>();

    public String getKdState() {
        return kdState;
    }

    public void setKdState(String kdState) {
        this.kdState = kdState;
    }

    public List<TraceFlowBean> getTraces() {
        return traces;
    }

    public void setTraces(List<TraceFlowBean> traces) {
        this.traces = traces;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
