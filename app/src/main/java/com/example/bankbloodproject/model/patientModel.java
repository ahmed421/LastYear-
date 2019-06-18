package com.example.bankbloodproject.model;

public class patientModel {
    String id;
    String name ;
    String email;
    String password ;
    String phone_number ;
    String age ;
    String gender ;
    String latidute;
    String longtude;
    String bloodGroup;
    int bloodQuantity;


    public patientModel() {
    }


    public patientModel(String id,String name, String email, String password, String phone_number, String age, String gender) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.age = age;
        this.gender = gender;
    }

    public patientModel(String id, String latidute, String longtude, String bloodGroup) {
        this.id = id;
        this.latidute = latidute;
        this.longtude = longtude;
        this.bloodGroup = bloodGroup;
    }

    public patientModel(int bloodQuantity) {
        this.bloodQuantity = bloodQuantity;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLatidute() {
        return latidute;
    }

    public void setLatidute(String latidute) {
        this.latidute = latidute;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getBloodQuantity() {
        return bloodQuantity;
    }

    public void setBloodQuantity(int bloodQuantity) {
        this.bloodQuantity = bloodQuantity;
    }
}
