package com.example.user.visco.Model;

/**
 * Created by User on 5/19/2017.
 */

public class GridModel {
    private String title;
    private int image;

    public GridModel(){

    }
    public GridModel(String title,int image){
        this.image = image;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public int getImage(){
        return image;
    }
}
