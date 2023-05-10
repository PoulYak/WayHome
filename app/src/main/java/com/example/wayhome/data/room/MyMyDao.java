package com.example.wayhome.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyMyDao {
    @Insert
    void insert(MyMy pet);

    @Query("SELECT * FROM pets WHERE phone_number = :phone_number")
    List<MyMy> getPetsByOwnerId(String phone_number);

    @Query("SELECT * FROM pets")
    List<MyMy> getAllPets();
}
