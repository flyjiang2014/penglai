package com.penglai.haima.bean;

/**
 * Created by  on 2019/11/22.
 * 文件说明：
 * "content": "木瓜膏唇膜润唇膏万用膏婴儿",
 * "id": 8,
 * "image_name": "mgg_25",
 * "model": "25g",
 * "number": 10,
 * "price": "25",
 * "provider_id": "ORG00001",
 * "title": "lucas papaw"
 */
public class ShopProductBean {
    private int id;
    private String content = "";
    private String image_name = "";
    private String model = "";
    private String number = "";
    private String price = "";
    private String provider_id = "";
    private String title = "";
    private int choose_number;

    public int getId() {
        return id;
    }

    public String getImage_name() {
        return image_name;
    }

    public String getModel() {
        return model;
    }

    public String getNumber() {
        return number;
    }

    public String getPrice() {
        return price;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getChoose_number() {
        return choose_number;
    }

    public void setChoose_number(int choose_number) {
        this.choose_number = choose_number;
    }

    public void setId(int id) {
        this.id = id;
    }
}
