package com.penglai.haima.bean;

import java.io.Serializable;

/**
 * Created by  on 2019/11/22.
 * 文件说明：
 * id: 1,
 * provider_address: "宜兴市阳羡东路888号",
 * provider_city: "",
 * provider_cover_image: "lgqzyy",
 * provider_district: "",
 * provider_enabled: "1",
 * provider_id: "ORG00001",
 * provider_name: "龙格亲子游泳俱乐部",
 * provider_phone: "13616151289",
 * provider_province: ""
 */
public class ShopDataBean implements Serializable {
    private String id = "";
    private String provider_address = "";
    private String provider_city = "";
    private String provider_cover_image = "";
    private String provider_district = "";
    private String provider_enabled = "";
    private String provider_id = "";
    private String provider_name = "";
    private String provider_phone = "";
    private String provider_province = "";

    public String getId() {
        return id;
    }

    public String getProvider_address() {
        return provider_address;
    }

    public String getProvider_city() {
        return provider_city;
    }

    public String getProvider_cover_image() {
        return provider_cover_image;
    }

    public String getProvider_district() {
        return provider_district;
    }

    public String getProvider_enabled() {
        return provider_enabled;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public String getProvider_phone() {
        return provider_phone;
    }

    public String getProvider_province() {
        return provider_province;
    }
}
