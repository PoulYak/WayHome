package com.example.wayhome.data.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PetDao {
    @Insert
    void insertAll(Pet... pets);

    @Query("SELECT * FROM pet")
    LiveData<List<Pet>> getAllPets();

}
