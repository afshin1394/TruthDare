package com.afshin.truthordare.Models;

import java.util.Arrays;

public class BottleModel {
    private String name;
    private byte[] image;
    private int IsPurchased;
    private int category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getIsPurchased() {
        return IsPurchased;
    }

    public void setIsPurchased(int isPurchased) {
        IsPurchased = isPurchased;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BottleModel{" +
                "name='" + name + '\'' +
                ", image=" + Arrays.toString(image) +
                ", IsPurchased=" + IsPurchased +
                ", category=" + category +
                '}';
    }
}
