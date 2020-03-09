package com.penglai.haima.bean;

/**
 * Created by  on 2020/3/7.
 * 文件说明：
 * "APP_ID": "hmj",
 * "APP_CONTENT": "增加了商家模块",
 * "DOWNLOAD_URL": "http://120.55.61.20:80/ypimp/appupdate/3_1.0.0.0.apk",
 * "APP_NAME": "3_1.0.0.0",
 * "UPDATE_ID": 3,
 * "APP_SIZE": "1300",
 * "APP_VERSION": "1.0.0.0",
 * "UPDATE_INSTALL": "1"
 */
public class UpdateDataBean {
    private String app_content;
    private String download_url;
    private String app_name;
    private int update_id;
    private String app_size;
    private String app_verson;
    private String update_install;

    public String getApp_content() {
        return app_content;
    }

    public String getDownload_url() {
        return download_url;
    }

    public String getApp_name() {
        return app_name;
    }

    public int getUpdate_id() {
        return update_id;
    }

    public String getApp_size() {
        return app_size;
    }

    public String getApp_verson() {
        return app_verson;
    }

    public String getUpdate_install() {
        return update_install;
    }
}
