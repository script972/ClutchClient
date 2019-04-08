package com.script972.clutchclient.domain.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.Data;

import static androidx.room.ForeignKey.CASCADE;

@Data
@Entity
public class CompanyEntity {

        @PrimaryKey(autoGenerate = true)
        private long id;

        @ColumnInfo(name = "title")
        private long title;

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public long getTitle() {
                return title;
        }

        public void setTitle(long title) {
                this.title = title;
        }
}
