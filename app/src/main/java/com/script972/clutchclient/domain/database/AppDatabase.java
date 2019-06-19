package com.script972.clutchclient.domain.database;

import com.script972.clutchclient.domain.database.dao.CardDao;
import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.domain.database.entity.CompanyEntity;
import com.script972.clutchclient.domain.database.entity.UserEntity;

import org.jetbrains.annotations.NotNull;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {CardEntity.class, CompanyEntity.class, UserEntity.class, CardEntity.CardItemUserJoin.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Migration handler. For future
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NotNull SupportSQLiteDatabase database) {

        }
    };

    public abstract CardDao cardDao();

}
