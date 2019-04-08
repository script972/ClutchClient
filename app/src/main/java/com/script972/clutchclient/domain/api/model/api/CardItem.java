package com.script972.clutchclient.domain.api.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CardItem{

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
    private Company company;
    @SerializedName("dateAdded")
    @Expose
    private long dateAdded;

    @SerializedName("facePhoto")
    @Expose
    private String facePhoto;

    @SerializedName("backPhoto")
    @Expose
    private String backPhoto;

    public CardItem() {
    }


}