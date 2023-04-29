package com.example.wayhome.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insertAll(Person... persons);

    @Query("SELECT * FROM person")
    List<Person> getAllPersons();

}
