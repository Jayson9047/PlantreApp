package com.example.plantreapp.connection;

public class WaterInfo {
    private int progress;
    private String btnName, text;

    public WaterInfo(int progress, String btnName, String text) {
        this.progress = progress;
        this.btnName = btnName;
        this.text = text;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
