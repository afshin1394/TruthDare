package com.afshin.truthordare.Service.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract  class BaseResponse<T> {


    public BaseResponse() {
    }

    @SerializedName("results")
    @Expose
    private List<T> result;


    @SerializedName("isFaulted")
    @Expose
    private boolean isFaulted;


    @SerializedName("isCompletedSuccessfully")
    @Expose
    private boolean isCompletedSuccessfully;


    @SerializedName("exception")
    @Expose
    private String  exception;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public boolean isFaulted() {
        return isFaulted;
    }

    public void setFaulted(boolean faulted) {
        isFaulted = faulted;
    }

    public boolean isCompletedSuccessfully() {
        return isCompletedSuccessfully;
    }

    public void setCompletedSuccessfully(boolean completedSuccessfully) {
        isCompletedSuccessfully = completedSuccessfully;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "result=" + result +
                ", isFaulted=" + isFaulted +
                ", isCompletedSuccessfully=" + isCompletedSuccessfully +
                ", exception='" + exception + '\'' +
                '}';
    }



}
