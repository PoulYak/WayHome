package com.example.wayhome.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pet {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    @ColumnInfo(name = "nickname")
    public String nickname;

    @ColumnInfo(name = "sex")
    public String sex;

    @ColumnInfo(name = "phone_number")
    public String phoneNumber;

    @ColumnInfo(name = "lost_day")
    public String lostDay;

    @ColumnInfo(name = "birth_day")
    public String birthDay;

    @ColumnInfo(name = "breed")
    public String breed;

    @ColumnInfo(name = "collar")
    public String collar;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "chip_number")
    public String chipNumber;

    @ColumnInfo(name = "stigma_number")
    public String stigmaNumber;

    @ColumnInfo(name = "comment")
    public String comment;

    @ColumnInfo(name = "features")
    public String features;

    @ColumnInfo(name = "longitude")
    public String longitude;

    @ColumnInfo(name = "latitude")
    public String latitude;

    public Pet(int userId, String nickname, String sex, String phoneNumber, String lostDay, String birthDay, String breed, String collar, String color, String chipNumber, String stigmaNumber, String comment, String features, String longitude, String latitude) {
        this.userId = userId;
        this.nickname = nickname;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.lostDay = lostDay;
        this.birthDay = birthDay;
        this.breed = breed;
        this.collar = collar;
        this.color = color;
        this.chipNumber = chipNumber;
        this.stigmaNumber = stigmaNumber;
        this.comment = comment;
        this.features = features;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

