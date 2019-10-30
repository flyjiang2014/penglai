package com.penglai.haima.bean;

/**
 * Created by  on 2019/10/30.
 * 文件说明：
 */
public class OrderProductItemBean {
    private String image_name = "";
    private String choose_number = "";
    private String price = "";
    private String model = "";
    private String id = "";
    private String title = "";
    private String content = "";

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getChoose_number() {
        return choose_number;
    }

    public void setChoose_number(String choose_number) {
        this.choose_number = choose_number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
