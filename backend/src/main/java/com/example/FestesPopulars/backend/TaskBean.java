package com.example.FestesPopulars.backend;

/** The object model for the data we are sending through endpoints */
public class TaskBean {

    private Long id;
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}