package com.example.aleknik.execomhackathon2017.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.repository.SaleItemDAORepository;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EActivity(R.layout.activity_new_sale_item)
public class NewSaleItemActivity extends AppCompatActivity {

    @ViewById
    TextView name;

    @ViewById
    TextView description;

    @ViewById
    TextView price;

    @ViewById
    Button add;

    @Bean
    UserDAORepository userDAORepository;

    @Bean
    SaleItemDAORepository saleItemDAORepository;

    @Click
    void add() {

        if (name.getText().toString().equals("") ||
                description.getText().toString().equals("") ||
                price.getText().toString().equals("")) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
            return;
        }

        SaleItem saleItem = new SaleItem(name.getText().toString(),
                description.getText().toString(), new Date(),
                Double.parseDouble(price.getText().toString()));
        saleItem.setUser(userDAORepository.getLoggedInUser());

        saleItemDAORepository.create(saleItem);
        setResult(RESULT_OK);
        finish();

    }
}
