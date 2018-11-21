package com.yoie.com.clockapp.Model;

import android.databinding.Bindable;
import android.util.Log;

public class Clock {

    public Clock(String name, String date, String content) {
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public Clock(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {

        return id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }


    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String name;
    private String date;
    private String content;
    private String number;

    public void click(){
        Log.e("Clock" + number, "clicked");

    }
}
