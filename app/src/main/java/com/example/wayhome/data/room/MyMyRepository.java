package com.example.wayhome.data.room;


import java.util.List;

public class MyMyRepository {
    private final MyMyDao petDao;

    public MyMyRepository(AppDatabase appDatabase) {
        this.petDao = appDatabase.mymyDao();
    }

    public void insertPet(MyMy pet) {
        new Thread(() -> petDao.insert(pet)).start();
    }

    public List<MyMy> getPetsByOwnerId(String phone_number) {
        return petDao.getPetsByOwnerId(phone_number);
    }
}