package com.penglai.haima.bean;

import java.io.Serializable;

/**
 * Created by ${flyjiang} on 2019/10/23.
 * 文件说明：content: "纸尿裤",
 * image_name: "ynj_hj",
 * model: "NB/S/M/L",
 * number: 0,
 * price: "119",
 * title: "Moony皇家"
 */
public class ProductSelectBean implements Serializable {
    private int id;
    private String content = "";
    private String image_name = "";
    private String model = "";
    private String price = "";
    private String title = "";
    private int choose_number;

    public ProductSelectBean(int id, String content, String image_name, String model, String price, String title, int choose_number) {
        this.id = id;
        this.content = content;
        this.image_name = image_name;
        this.model = model;
        this.price = price;
        this.title = title;
        this.choose_number = choose_number;
    }

    public String getContent() {
        return content;
    }

    public String getImage_name() {
        return image_name;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChoose_number() {
        return choose_number;
    }

    public void setChoose_number(int choose_number) {
        this.choose_number = choose_number;
    }
}
