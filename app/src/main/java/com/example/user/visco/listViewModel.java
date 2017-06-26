package com.example.user.visco;

/**
 * Created by User on 5/12/2017.
 */

public class listViewModel {

    String title = "";
    String desc = "";
    String image;
    private String item_key="";
    private double price;
     public listViewModel(){

     }
    public listViewModel(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image =image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDesc() {

        return desc;
    }
    public String getItem_key(){
        return item_key;
    }
    public double getPrice(){
        return price;
    }
}
