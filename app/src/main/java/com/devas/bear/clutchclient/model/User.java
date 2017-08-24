package com.devas.bear.clutchclient.model;

/**
 * Created by script972 on 24.08.2017.
 */

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int idFacebook;
    private int countCard;
    private int rang;
    private String photo;

    public User(long id, String firstName, String lastName, String email, int idFacebook, int countCard, int rang, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.idFacebook = idFacebook;
        this.countCard = countCard;
        this.rang = rang;
        this.photo = photo;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(int idFacebook) {
        this.idFacebook = idFacebook;
    }

    public int getCountCard() {
        return countCard;
    }

    public void setCountCard(int countCard) {
        this.countCard = countCard;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
