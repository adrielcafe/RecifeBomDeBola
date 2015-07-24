package com.adrielcafe.recifebomdebola.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

public class Photo implements AsymmetricItem {
    public String url;

    private int position;
    private int columnSpan;
    private int rowSpan;

    public Photo(String url){
        this.url = url;
        position = 0;
        columnSpan = 2;
        rowSpan = columnSpan;
    }

    public Photo(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int getColumnSpan() {
        return columnSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(columnSpan);
        dest.writeInt(rowSpan);
        dest.writeInt(position);
    }

    @Override
    public String toString() {
        return String.format("%s: %sx%s", position, rowSpan, columnSpan);
    }

    public int getPosition() {
        return position;
    }

    private void readFromParcel(Parcel in) {
        columnSpan = in.readInt();
        rowSpan = in.readInt();
        position = in.readInt();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(@NonNull Parcel in) {
            return new Photo(in);
        }
        @NonNull
        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}