package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aleknik.execomhackathon2017.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    TextView registerLink;

    @Click(R.id.registerLink)
    void register() {
        RegistrationActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_NO_HISTORY).start();
    }

}
