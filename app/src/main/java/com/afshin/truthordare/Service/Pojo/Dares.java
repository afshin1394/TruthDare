package com.afshin.truthordare.Service.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dares {

    @SerializedName("categoryID")
    @Expose
    private int categoryID;


    @SerializedName("CategoryName")
    @Expose
    private String CategoryName;


    @SerializedName("dareStr")
    @Expose
    private String dareStr;

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getDareStr() {
        return dareStr;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setDareStr(String dareStr) {
        this.dareStr = dareStr;
    }
}
