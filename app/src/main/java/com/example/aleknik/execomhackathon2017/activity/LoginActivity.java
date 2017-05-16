package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;
import com.example.aleknik.execomhackathon2017.model.User;
import com.example.aleknik.execomhackathon2017.preference.UserPreferences_;

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
            finish();
        } else {
            Toast.makeText(this, "Login failed!", Toast.LENGTH_LONG).show();
        }
    }



}
