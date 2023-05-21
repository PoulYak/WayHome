package com.example.wayhome.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pets")
public class MyMy {
    @PrimaryKey(autoGenerate = true)
    private int roomId;

    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "postImage")
    private int postImage;
    @ColumnInfo(name = "nickname")
    private String nickname = "-";
    @ColumnInfo(name = "status")
    private String status = "Потерян";
    @ColumnInfo(name = "breed")
    private String breed = "Йорк";
    @ColumnInfo(name = "sex")
    private String sex = "-";
    @ColumnInfo(name = "birthday")
    private String birthday = "-";
    @ColumnInfo(name = "color")
    private String color = "-";
    @ColumnInfo(name = "chip_number")
    private String chip_number = "-";
    @ColumnInfo(name = "stigma_number")
    private String stigma_number = "-";
    @ColumnInfo(name = "phone_number")
    private String phone_number = "-";
    @ColumnInfo(name = "comment")
    private String comment = "-";
    @ColumnInfo(name = "features")
    private String features = "-";
    @ColumnInfo(name = "latitude")
    private double latitude = 0;
    @ColumnInfo(name = "longitude")
    private double longitude = 0;
    @ColumnInfo(name = "collar")
    private String collar = "";
    @ColumnInfo(name = "image_path")
    private String image_path = "";
    @ColumnInfo(name = "placeComment")
    private String placeComment = "";
    @ColumnInfo(name = "owner_mail")
    private String owner_mail = "";
    @ColumnInfo(name = "active")
    private String active = "true";


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getOwner_mail() {
        return owner_mail;
    }

    public void setOwner_mail(String owner_mail) {
        this.owner_mail = owner_mail;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceComment() {
        return placeComment;
    }

    public void setPlaceComment(String placeComment) {
        this.placeComment = placeComment;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public MyMy(int postImage, String nickname, String status, String breed) {
        this.postImage = postImage;
        this.nickname = nickname;
        this.status = status;
        this.breed = breed;
    }

    public MyMy(int postImage, String nickname, String status, String breed, String sex, String birthday, String color, String chip_number, String stigma_number, String phone_number, String comment, String features, float latitude, float longitude, boolean have_collar) {
        this.postImage = postImage;
        this.nickname = nickname;
        this.status = status;
        this.breed = breed;
        this.sex = sex;
        this.birthday = birthday;
        this.color = color;
        this.chip_number = chip_number;
        this.stigma_number = stigma_number;
        this.phone_number = phone_number;
        this.comment = comment;
        this.features = features;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCollar() {
        return collar;
    }

    public void setCollar(String collar) {
        this.collar = collar;
    }

    public MyMy() {
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getChip_number() {
        return chip_number;
    }

    public void setChip_number(String chip_number) {
        this.chip_number = chip_number;
    }

    public String getStigma_number() {
        return stigma_number;
    }

    public void setStigma_number(String stigma_number) {
        this.stigma_number = stigma_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getPostImage() {
        return postImage;
    }

    public String getNickname() {
        return nickname;
    }

    public String getStatus() {
        return status;
    }

    public String getBreed() {
        return breed;
    }
}
