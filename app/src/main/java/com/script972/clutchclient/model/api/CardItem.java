package com.script972.clutchclient.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("auther")
    @Expose
    private User auther;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("accessUsers")
    @Expose
    private List<User> accessUsers = null;
    @SerializedName("company")
    @Expose
    private Object company;
    @SerializedName("dateAdded")
    @Expose
    private Object dateAdded;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuther() {
        return auther;
    }

    public void setAuther(User auther) {
        this.auther = auther;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<User> getAccessUsers() {
        return accessUsers;
    }

    public void setAccessUsers(List<User> accessUsers) {
        this.accessUsers = accessUsers;
    }

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public Object getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Object dateAdded) {
        this.dateAdded = dateAdded;
    }
}