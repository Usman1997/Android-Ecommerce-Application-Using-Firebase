package com.example.user.visco.Model;

/**
 * Created by User on 5/9/2017.
 */

public class ListModel {
    private String title;
    private String desc;
    private String image;


    public ListModel(){

    }

    public ListModel(String title,String desc,String image){
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }


    public void setImage(String image){
        this.image = image;
    }
    public String getTitle(){
        return title;
    }
    public String getDesc(){
        return desc;
    }

    public String getImage(){
        return image;
    }
}
