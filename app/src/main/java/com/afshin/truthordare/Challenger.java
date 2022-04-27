package com.afshin.truthordare;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Challenger  implements Parcelable {
    private static final String TAG = "Challenger";

    public Challenger() {
    }

    public Challenger(String name, Uri image, int color) {
        this.name = name;
        this.color = color;
        this.image =image;
    }
    int id;
    String name;
    Uri image;
    int color = 0;
    double startAngle;
    double endAngle;

    Bitmap imageCustom;

    protected Challenger(Parcel in) {
        id  = in.readInt();
        name = in.readString();
        color = in.readInt();
        startAngle = in.readDouble();
        endAngle = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        if (color == 0) {
            return 0;
        }
        return color;
    }


    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public static final Creator<Challenger> CREATOR = new Creator<Challenger>() {
        @Override
        public Challenger createFromParcel(Parcel in) {
            return new Challenger(in);
        }

        @Override
        public Challenger[] newArray(int size) {
            return new Challenger[size];
        }
    };


    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEmpty() {
        return name == null || name.isEmpty();
    }



    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(color);
        parcel.writeDouble(startAngle);
        parcel.writeDouble(endAngle);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "onTextChanged: " + s);
    }

    public Bitmap getImageCustom() {
        return imageCustom;
    }

    public void setImageCustom(Bitmap imageCustom) {
        this.imageCustom = imageCustom;
    }

    // Function to check if value is empty

    @Override
    public String toString() {
        return "Challenger{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", color=" + color +
                ", startAngle=" + startAngle +
                ", endAngle=" + endAngle +
                '}';
    }
}
