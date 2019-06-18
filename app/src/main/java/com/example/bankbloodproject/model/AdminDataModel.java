package com.example.bankbloodproject.model;

public class AdminDataModel {
    String address;
    int age;
    String email;
    String gender;
    String name;

    public AdminDataModel(String address, int age, String email, String gender, String name) {
        this.address = address;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.name = name;
    }

    public AdminDataModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
