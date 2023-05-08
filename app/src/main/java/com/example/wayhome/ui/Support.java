package com.example.wayhome.ui;

public class Support {
    private String phone;
    private String email;
    private String name;
    private String message;

    public Support(String phone, String email, String name, String message) {
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public Support() {
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
