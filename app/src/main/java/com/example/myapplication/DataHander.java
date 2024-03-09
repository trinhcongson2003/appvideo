package com.example.myapplication;

public class DataHander {

    String tiltle, url;

    public DataHander(String tiltle, String url){
        this.tiltle = tiltle;
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
    public String getTiltle(){
        return tiltle;
    }
}
