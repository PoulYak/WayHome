package com.example.wayhome.data.room;

import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.User;
import com.example.wayhome.data.room.UserDao;

import java.util.List;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(AppDatabase appDatabase) {
        this.userDao = appDatabase.userDao();
    }

    public void insertUser(User user) {
        new Thread(() -> userDao.insert(user)).start();
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}