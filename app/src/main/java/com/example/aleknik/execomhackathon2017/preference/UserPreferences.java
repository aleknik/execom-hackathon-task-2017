package com.example.aleknik.execomhackathon2017.preference;


import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface UserPreferences {

    long id();

}

