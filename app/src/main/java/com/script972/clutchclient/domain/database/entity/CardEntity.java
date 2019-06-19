package com.script972.clutchclient.domain.database.entity;

import androidx.annotation.NonNull;
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

    @Data//https://commonsware.com/AndroidArch/previews/mn-relations-in-room
    @Entity (
            tableName = "card_item_join",
            primaryKeys = {"cardId", "userId"},
            foreignKeys = {
                    @ForeignKey(
                            entity = ,

                    )
            }
    )
    public static class CardItemUserJoin {
        @NonNull
        public final String cardId;
        @NonNull
        public final String userId;

    }
}
