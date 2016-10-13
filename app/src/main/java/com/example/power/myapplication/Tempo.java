package com.example.power.myapplication;

/**
 * Created by Power on 19/08/2016.
 */
public class Tempo {

    String Data;
    int Code;
    String Day;
    String High;
    String Low;
    String Text;

    public Tempo(String data, String day, String low, String high, String text, int code) {
        Data = data;
        Day = day;
        Low = low;
        High = high;
        Text = text;
        Code = code;
    }
    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }



    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getLow() {
        return Low;
    }

    public void setLow(String low) {
        Low = low;
    }

    public String getHigh() {
        return High;
    }

    public void setHigh(String high) {
        High = high;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

}
