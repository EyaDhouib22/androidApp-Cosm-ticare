package com.example.cosmticare.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    public long id;
    public int imageResId;
    public String name;
    public String price;
    public String availability;
    public String description;
    public boolean isFavorite = false;


    public Product(long id, int imageResId, String name, String price, String availability, String description) {
        this.id = id;
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.description = description;
    }


    protected Product(Parcel in) {
        id = in.readLong();
        imageResId = in.readInt();
        name = in.readString();
        price = in.readString();
        availability = in.readString();
        description = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(imageResId);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(availability);
        dest.writeString(description);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }

    public long getId() { return id; }
    public String getName() { return name; }

}
