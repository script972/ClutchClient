package com.script972.clutchclient.domain.api.model.api;

public class PhoneNumber {


    private String name;

    private String phoneNumber;

    public PhoneNumber() {
    }

    public PhoneNumber(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
