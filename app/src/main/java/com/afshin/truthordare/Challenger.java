package com.afshin.truthordare;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Challenger implements Parcelable {

        public Challenger ( String name, int color ) {
            this.name = name;
            this.color = color;
        }

        String name;
        int color;
        double startAngle;
        double endAngle;

    protected Challenger(Parcel in) {
        name = in.readString();
        color = in.readInt();
        startAngle = in.readDouble();
        endAngle = in.readDouble();
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

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
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
       parcel.writeString(name);
       parcel.writeInt(color);
       parcel.writeDouble(startAngle);
       parcel.writeDouble(endAngle);
    }

    @Override
    public String toString() {
        return "Challenger{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", startAngle=" + startAngle +
                ", endAngle=" + endAngle +
                '}';
    }
}
