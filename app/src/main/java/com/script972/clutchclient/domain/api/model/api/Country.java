package com.script972.clutchclient.domain.api.model.api;

public class Country {

    private long id;

    private String title;

    /**
     * Prefix of phone number
     */
    private String codeNumber;

    private Position position;

    private String notice;

    public Country() {
    }

    public Country(long id, String title, String codeNumber, Position position, String notice) {
        this.id = id;
        this.title = title;
        this.codeNumber = codeNumber;
        this.position = position;
        this.notice = notice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", codeNumber='" + codeNumber + '\'' +
                ", position=" + position +
                ", notice='" + notice + '\'' +
                '}';
    }

}
