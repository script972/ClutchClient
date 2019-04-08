package com.script972.clutchclient.domain.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import lombok.Data;

import static androidx.room.ForeignKey.CASCADE;

@Data
@Entity(foreignKeys = {@ForeignKey(entity = CompanyEntity.class,
        parentColumns = "id",
        childColumns = "company_id",
        onDelete = CASCADE) })
public class CardEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String number;

    @ColumnInfo(name = "company_id", index = true)
    private Long companyId;

    @ColumnInfo(name = "photo_front_local")
    private String photoFrontLocal;

    @ColumnInfo(name = "photo_back_local")
    private String photoBackLocal;

    @ColumnInfo(name = "photo_front_server")
    private String photoFrontServer;

    @ColumnInfo(name = "photo_back_server")
    private String photoBackServer;

    @ColumnInfo(name = "date_added")
    private long dateAdded;

    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getPhotoFrontLocal() {
        return photoFrontLocal;
    }

    public void setPhotoFrontLocal(String photoFrontLocal) {
        this.photoFrontLocal = photoFrontLocal;
    }

    public String getPhotoBackLocal() {
        return photoBackLocal;
    }

    public void setPhotoBackLocal(String photoBackLocal) {
        this.photoBackLocal = photoBackLocal;
    }

    public String getPhotoFrontServer() {
        return photoFrontServer;
    }

    public void setPhotoFrontServer(String photoFrontServer) {
        this.photoFrontServer = photoFrontServer;
    }

    public String getPhotoBackServer() {
        return photoBackServer;
    }

    public void setPhotoBackServer(String photoBackServer) {
        this.photoBackServer = photoBackServer;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
