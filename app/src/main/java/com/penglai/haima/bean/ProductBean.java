package com.penglai.haima.bean;

/**
 * Created by ${flyjiang} on 2019/10/23.
 * 文件说明：content: "纸尿裤",
 * image_name: "ynj_hj",
 * model: "NB/S/M/L",
 * number: 0,
 * price: "119",
 * title: "Moony皇家"
 */
public class ProductBean {
    private int id;
    private String content = "";
    private String image_name = "";
    private String model = "";
    private int number;
    private String price = "";
    private String title = "";

    private int choose_number;

    public String getContent() {
        return content;
    }

    public String getImage_name() {
        return image_name;
    }

    public String getModel() {
        return model;
    }

    public int getNumber() {
        return number;
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

    public void setNumber(int number) {
        this.number = number;
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
