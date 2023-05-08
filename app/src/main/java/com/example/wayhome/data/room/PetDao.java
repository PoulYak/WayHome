package com.example.wayhome.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PetDao {
    @Insert
    void insert(Pet pet);

    @Query("SELECT * FROM pets WHERE owner_id = :ownerId")
    List<Pet> getPetsByOwnerId(int ownerId);

    @Query("SELECT * FROM pets")
    List<Pet> getAllPets();
}
