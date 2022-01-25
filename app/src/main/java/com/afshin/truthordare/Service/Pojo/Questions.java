package com.afshin.truthordare.Service.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Questions {


    @SerializedName("categoryID")
    @Expose
    private int categoryID;


    @SerializedName("CategoryName")
    @Expose
    private String CategoryName;


    @SerializedName("questionStr")
    @Expose
    private String questionStr;

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }
}
