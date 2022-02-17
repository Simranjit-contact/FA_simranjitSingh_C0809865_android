package com.example.fa_simranjitsingh_c0809865_android;
//Created by Simranjit Singh C0809865 on 16-02-2022

import android.os.Parcel;
import android.os.Parcelable;

public class AddPlaceModel implements Parcelable {

    private int id;
    private String name;
    private String  lat;
    private String lng;
    private String timestamp;
    private String completed;

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public AddPlaceModel(int id, String name, String lat, String lng, String timestamp, String completed) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
        this.completed = completed;
    }

    public AddPlaceModel(){

    }

    public static final String TABLE_NAME = "table_places";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_COMPLETED = "completed";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_LAT + " TEXT,"
                    + COLUMN_COMPLETED + " TEXT,"
                    + COLUMN_LNG + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    protected AddPlaceModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lat = in.readString();
        lng = in.readString();
        timestamp = in.readString();
        completed = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(timestamp);
        dest.writeString(completed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddPlaceModel> CREATOR = new Creator<AddPlaceModel>() {
        @Override
        public AddPlaceModel createFromParcel(Parcel in) {
            return new AddPlaceModel(in);
        }

        @Override
        public AddPlaceModel[] newArray(int size) {
            return new AddPlaceModel[size];
        }
    };
}
