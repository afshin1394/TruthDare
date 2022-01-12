package com.afshin.truthordare.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ParcelArrayList extends ArrayList implements Parcelable {
    protected ParcelArrayList(Parcel in) {
    }

    public static final Creator<ParcelArrayList> CREATOR = new Creator<ParcelArrayList>() {
        @Override
        public ParcelArrayList createFromParcel(Parcel in) {
            return new ParcelArrayList(in);
        }

        @Override
        public ParcelArrayList[] newArray(int size) {
            return new ParcelArrayList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
