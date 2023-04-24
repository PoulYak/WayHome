package com.example.wayhome.ui.profile;
public class MyMy {
    private int postImage;
    private String nickname;
    private String status;
    private String breed;

    public MyMy(int postImage, String nickname, String status, String breed) {
        this.postImage = postImage;
        this.nickname = nickname;
        this.status = status;
        this.breed = breed;
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
