package com.example.aleknik.execomhackathon2017.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
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

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends AppCompatActivity {

    @ViewById
    EditText email;

    @ViewById
    EditText password;

    @ViewById
    EditText name;

    @ViewById
    EditText contact;

    @Pref
    UserPreferences_ userPreferences;

    @Bean
    UserDAORepository userDAORepository;

    @EditorAction(R.id.password)
    @Click(R.id.register)
    void register() {
        if (email.getText().toString().equals("") ||
                password.getText().toString().equals("") ||
                name.getText().toString().equals("") ||
                contact.getText().toString().equals("")) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User();

        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setName(name.getText().toString());
        user.setContact(contact.getText().toString());

        if (!userDAORepository.emailTaken(user.getEmail())) {
            userDAORepository.register(user);
            Toast.makeText(this, "Registration successful.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Registration failed, email already exists.", Toast.LENGTH_LONG).show();
        }
    }
}
