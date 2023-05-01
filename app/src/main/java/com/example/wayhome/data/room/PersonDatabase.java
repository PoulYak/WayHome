package com.example.wayhome.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
    public static PersonDatabase INSTANCE;

    public static PersonDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (PersonDatabase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PersonDatabase.class, "person-database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
