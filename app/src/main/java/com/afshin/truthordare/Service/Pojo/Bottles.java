package com.afshin.truthordare.Service.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bottles {

    @SerializedName("bottleGuid")
    @Expose
    private String  bottleGuid;

    @SerializedName("categoryId")
    @Expose
    private int categoryId;


    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("isPurchased")
    @Expose
    private int isPurchased;


    public String getBottleGuid() {
        return bottleGuid;
    }

    public void setBottleGuid(String bottleGuid) {
        this.bottleGuid = bottleGuid;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(int isPurchased) {
        this.isPurchased = isPurchased;
    }
}
