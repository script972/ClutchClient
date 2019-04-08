package com.script972.clutchclient.domain.api.model.api;

public class City {

    private long id;

    private String city;

    private String notice;

    private Country country;

    private Position position;

    public City() {
    }

    public City(long id, String city, String notice, Country country, Position position) {
        this.id = id;
        this.city = city;
        this.notice = notice;
        this.country = country;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", notice='" + notice + '\'' +
                ", country=" + country +
                ", position=" + position +
                '}';
    }
}
