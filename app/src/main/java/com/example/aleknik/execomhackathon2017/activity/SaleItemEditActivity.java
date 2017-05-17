package com.example.aleknik.execomhackathon2017.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.repository.SaleItemDAORepository;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

@EActivity(R.layout.activity_sale_item_edit)
public class SaleItemEditActivity extends AppCompatActivity {

    @Extra
    String item;

    @ViewById
    TextView name;

    @ViewById
    TextView description;

    @ViewById
    TextView price;

    @ViewById
    Button save;

    @Bean
    UserDAORepository userDAORepository;

    @Bean
    SaleItemDAORepository saleItemDAORepository;

    private final Gson gson = new Gson();

    private SaleItem saleItem;

    @AfterViews
    void init() {
        saleItem = gson.fromJson(item, SaleItem.class);
        initData();
    }

    void initData() {
        name.setText(saleItem.getName());
        description.setText(saleItem.getDescription());
        price.setText(String.format(Locale.ENGLISH, "%.2f", saleItem.getPrice()));
    }

    @Click
    void save() {
        if (name.getText().toString().equals("") ||
                description.getText().toString().equals("") ||
                price.getText().toString().equals("")) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
            return;
        }

        saleItem.setName(name.getText().toString());
        saleItem.setDescription(description.getText().toString());
        saleItem.setPrice(Double.parseDouble(price.getText().toString()));

        saleItem = saleItemDAORepository.update(saleItem);
        userDAORepository.refresh(saleItem.getUser());

        Intent intent = new Intent();
        intent.putExtra("saleItem", gson.toJson(saleItem));
        setResult(RESULT_OK, intent);
        finish();
    }
}
