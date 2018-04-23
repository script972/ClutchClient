package com.script972.clutchclient.model;

/**
 * Created by script972 on 24.08.2017.
 */

public class CardModel {


    private long id;
    private String name;
    private int rang;
    private String thumbnail;
    private String thumbnailback;

    private String barcode;
    private String company;
    private String description;
    private User auther;

    public CardModel(long id, String name, int rang, String thumbnail, String thumbnailback, String barcode, String company, String description, User auther) {
        this.id = id;
        this.name = name;
        this.rang = rang;
        this.thumbnail = thumbnail;
        this.thumbnailback = thumbnailback;
        this.barcode = barcode;
        this.company = company;
        this.description = description;
        this.auther = auther;
    }

    public CardModel(String name, int rang, String thumbnail) {
        this.name = name;
        this.rang = rang;
        this.thumbnail = thumbnail;
    }

    public CardModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailback() {
        return thumbnailback;
    }

    public void setThumbnailback(String thumbnailback) {
        this.thumbnailback = thumbnailback;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuther() {
        return auther;
    }

    public void setAuther(User auther) {
        this.auther = auther;
    }
}
