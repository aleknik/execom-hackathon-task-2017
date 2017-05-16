package com.example.aleknik.execomhackathon2017.repository;


import com.example.aleknik.execomhackathon2017.database.DatabaseHelper;
import com.example.aleknik.execomhackathon2017.database.dao.UserDAO;
import com.example.aleknik.execomhackathon2017.model.User;
import com.example.aleknik.execomhackathon2017.preference.UserPreferences_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;

@EBean
public class UserDAORepository {

    @Pref
    UserPreferences_ userPreferences;

    @OrmLiteDao(helper = DatabaseHelper.class)
    UserDAO userDAO;

    public User logIn(String email, String password) {
        User user = null;

        try {
            user = userDAO.queryBuilder().where().eq("email", email).and().eq("password", password).queryForFirst();
            if (user != null) {
                userPreferences.id().put(user.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void register(User user) {
        userDAO.create(user);
        userPreferences.id().put(user.getId());
    }

    public boolean emailTaken(String email) {
        try {
            return userDAO.queryBuilder().where().eq("email", email).countOf() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User findById(long id) {
        return userDAO.queryForId(id);
    }

    public User getLoggedInUser() {
        return findById(userPreferences.id().get());
    }
}