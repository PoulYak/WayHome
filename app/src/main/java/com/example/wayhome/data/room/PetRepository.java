package com.example.wayhome.data.room;

import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.Pet;
import com.example.wayhome.data.room.PetDao;

import java.util.List;

public class PetRepository {
    private final PetDao petDao;

    public PetRepository(AppDatabase appDatabase) {
        this.petDao = appDatabase.petDao();
    }

    public void insertPet(Pet pet) {
        new Thread(() -> petDao.insert(pet)).start();
    }

    public List<Pet> getPetsByOwnerId(int ownerId) {
        return petDao.getPetsByOwnerId(ownerId);
    }
}
