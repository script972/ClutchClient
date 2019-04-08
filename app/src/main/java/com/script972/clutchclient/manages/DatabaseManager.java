package com.script972.clutchclient.manages;

import com.script972.clutchclient.core.ClutchApplication;
import com.script972.clutchclient.domain.database.AppDatabase;
import com.script972.clutchclient.domain.database.dao.CardDao;

import androidx.room.Room;

public class DatabaseManager {

    private static AppDatabase db;
    private static DatabaseManager ourInstance;

    public static DatabaseManager getInstance() {
        if(ourInstance == null){
            initDatabase();
        }
        return ourInstance;
    }

    private DatabaseManager() {
    }

    private static void initDatabase() {
        db = Room.databaseBuilder(ClutchApplication.getApplication(),
                AppDatabase.class, "clutchclient")
                .addMigrations(AppDatabase.MIGRATION_1_2).build();

        ourInstance = new DatabaseManager();
    }

    public CardDao getCardDao() {
        return db.cardDao();
    }


}
