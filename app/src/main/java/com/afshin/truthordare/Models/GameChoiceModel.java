package com.afshin.truthordare.Models;

import android.graphics.drawable.Drawable;

public class GameChoiceModel {
    private int id;
    private String title;
    private String body;
    private int image;
    private byte[] imageServer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public byte[] getImageServer() {
        return imageServer;
    }

    public void setImageServer(byte[] imageServer) {
        this.imageServer = imageServer;
    }
}
