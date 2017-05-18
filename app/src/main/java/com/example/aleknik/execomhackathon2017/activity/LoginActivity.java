package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.User;
import com.example.aleknik.execomhackathon2017.preference.UserPreferences_;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    TextView registerLink;

    @ViewById
    EditText email;

    @ViewById
    EditText password;

    @Pref
    UserPreferences_ userPreferences;

    @ViewById
    Button login;

    @Bean
    UserDAORepository userDAORepository;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Click(R.id.registerLink)
    void register() {
        RegistrationActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_NO_HISTORY).start();
    }

    @EditorAction(R.id.password)
    @Click
    void login() {
        final User user = userDAORepository.logIn(email.getText().toString(), password.getText().toString());

        if (user != null) {
            final Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            Toast.makeText(this, "Login successful.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Login failed!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }
}
