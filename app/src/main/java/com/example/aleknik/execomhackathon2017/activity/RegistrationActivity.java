package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aleknik.execomhackathon2017.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends AppCompatActivity {

    @ViewById
    TextView loginLink;

    @Click(R.id.loginLink)
    void login() {
        LoginActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT).start();
    }
}
