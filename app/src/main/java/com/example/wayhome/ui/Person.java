package com.example.wayhome.ui;


public class Person {
    private String name;

    private String email;

    private String phone;
    private int is_toggle;


    public Person() {
    }

    public Person(String name, String email, String phone, int is_toggle) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.is_toggle = is_toggle;
    }

    public int getIs_toggle() {
        return is_toggle;
    }

    public void setIs_toggle(int is_toggle) {
        this.is_toggle = is_toggle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
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
}
