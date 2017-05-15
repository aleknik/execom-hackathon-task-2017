package com.example.aleknik.execomhackathon2017.database.dao;


import com.example.aleknik.execomhackathon2017.model.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class UserDAO extends RuntimeExceptionDao<User, Long> {

    public UserDAO(Dao<User, Long> dao) {
        super(dao);
    }
}
