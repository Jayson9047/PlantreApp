package com.example.plantreapp.connection;

public class WaterInfo {
    private int percentage;
    private String btnName, text;

    public WaterInfo(int percentage, String btnName, String text) {
        this.percentage = percentage;
        this.btnName = btnName;
        this.text = text;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getText() {
        return text;
    }

    public void setText(String txt) {
        this.text = txt;
    }
}
