package com.script972.clutchclient.domain.database.dao;

import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.ui.model.CardItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CardDao {

    @Query("SELECT * FROM CardEntity")
    LiveData<List<CardEntity>> getAllList();

    @Query("SELECT * FROM CardEntity WHERE id=:cardId")
    LiveData<CardEntity> findCardById(long cardId);

    @Query("SELECT * FROM CardEntity WHERE id=:cardId")
    CardEntity getCardById(long cardId);

    @Insert
    void insert(CardEntity value);

    @Update
    void update(CardEntity value);

    @Query("SELECT * FROM CardEntity WHERE number=:number")
    CardEntity getCardByNumber(String number);

    @Query("DELETE FROM CardEntity WHERE id =:id")
    void removeCardById(int id);
}
