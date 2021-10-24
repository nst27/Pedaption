package com.example.pedaption;

import java.io.Serializable;

public class Model implements Serializable {
    String imageUrl;
    String name,petAge,petDescription,petAddress,petGender,type,fullName,phone;
    public Model(){

    }

    public Model(String name, String petAge, String petAddress, String petGender, String petDescription,String type,String fullName,String phone,String imageUrl){
        this.name = name;
        this.petAge = petAge;
        this.petAddress = petAddress;
        this.petGender = petGender;
        this.petDescription = petDescription;
        this.type = type;
        this.imageUrl = imageUrl;
        this.fullName = fullName;
        this.phone = phone;
    }

   public String getType(){
        return type;
   }

   public String getFullName(){
        return fullName;
   }

   public  String getPhone(){
        return  phone;
   }

   public void setFullName(String fullName){
        this.fullName = fullName;
   }

   public void setPhone(String phone){
        this.phone = phone;
   }
   public void setType(String type){
        this.type = type;
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

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
