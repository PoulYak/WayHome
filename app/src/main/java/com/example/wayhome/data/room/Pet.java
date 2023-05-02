package com.example.wayhome.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pets")
public class Pet {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "breed")
    private String breed;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "lost_day")
    private int lostDay;

    @ColumnInfo(name = "birthday")
    private int birthday;

    @ColumnInfo(name = "owner_id")
    private int ownerId;

    public Pet(int id, String name, String breed, String phoneNumber, int lostDay, int birthday, int ownerId) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.phoneNumber = phoneNumber;
        this.lostDay = lostDay;
        this.birthday = birthday;
        this.ownerId = ownerId;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getLostDay() {
        return lostDay;
    }

    public void setLostDay(int lostDay) {
        this.lostDay = lostDay;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}