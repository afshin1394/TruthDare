package com.afshin.truthordare.Utils;

import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.Utils.Enums.ToastType;

import java.time.Duration;

public class BaseInfo {

    private ToastType toastType;
    private ToastDuration duration;
    private String message;

    public BaseInfo(ToastType toastType, ToastDuration duration, String message) {
        this.toastType = toastType;
        this.duration = duration;
        this.message = message;
    }

    public ToastType getToastType() {
        return toastType;
    }

    public void setToastType(ToastType toastType) {
        this.toastType = toastType;
    }

    public ToastDuration getDuration() {
        return duration;
    }

    public void setDuration(ToastDuration duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "toastType=" + toastType +
                ", duration=" + duration +
                ", message='" + message + '\'' +
                '}';
    }
}
