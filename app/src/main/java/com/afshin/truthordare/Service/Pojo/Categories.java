package com.afshin.truthordare.Service.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {
    @SerializedName("categoryID")
    @Expose
    private int categoryID;


    @SerializedName("categoryName")
    @Expose
    private String categoryName;


    @SerializedName("categorySection")
    @Expose
    private String categorySection;

    @SerializedName("categoryDescription")
    @Expose
    private String categoryDescription;

    @SerializedName("categoryImage")
    @Expose
    private String categoryImage;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySection() {
        return categorySection;
    }

    public void setCategorySection(String categorySection) {
        this.categorySection = categorySection;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
