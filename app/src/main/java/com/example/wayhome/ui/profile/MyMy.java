package com.example.wayhome.ui.profile;
public class MyMy {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int postImage;
    private String nickname = "-";
    private String status = "Потерян";
    private String breed = "Йорк";
    private String sex = "-";
    private String birthday = "-";
    private String color = "-";
    private String chip_number = "-";
    private String stigma_number = "-";
    private String phone_number = "-";
    private String comment = "-";
    private String features = "-";
    private double latitude = 0;
    private double longitude = 0;
    private boolean have_collar = false;

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
        this.have_collar = have_collar;
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

    public boolean isHave_collar() {
        return have_collar;
    }

    public void setHave_collar(boolean have_collar) {
        this.have_collar = have_collar;
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
