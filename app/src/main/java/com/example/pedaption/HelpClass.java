package com.example.pedaption;

import android.net.Uri;

public class HelpClass {
    String name,petAge,petDescription,petAddress,petGender;

    int id;
    public HelpClass() {
    }

    public HelpClass(String name, String petAge, String petAddress, String petGender, String petDescription ) {
        this.name = name;
        this.petAge = petAge;
        this.petAddress = petAddress;
        this.petGender = petGender;
        this.petDescription = petDescription;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return petAge;
    }

    public void setAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetAddress() {
        return petAddress;
    }

    public void setPetAddress(String petAddress) {
        this.petAddress = petAddress;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetDescription(){
        return petDescription;
    }
    public void setPetDescription(){
        this.petDescription = petDescription;
    }



}
