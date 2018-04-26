package com.script972.clutchclient.model.api;

public class Position {

    private Long id;

    private double lat;

    private double lng;

    private String description;

    public Position() {
    }

    public Position(double lat, double lng) {
        this.lat=lat;
        this.lng=lng;
    }

    public Position(Long id, double lat, double lng, String description) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", description='" + description + '\'' +
                '}';
    }
}
