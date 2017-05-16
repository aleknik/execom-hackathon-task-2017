package com.example.aleknik.execomhackathon2017.activity;

import android.support.v7.app.AppCompatActivity;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

@EActivity(R.layout.activity_sale_item_details)
public class SaleItemDetailsActivity extends AppCompatActivity {

    @Extra
    String item;

    private SaleItem saleItem;

    @Bean
    UserDAORepository userDAORepository;

    @AfterViews
    void init() {
        final Gson gson = new Gson();
        saleItem = gson.fromJson(item, SaleItem.class);
        userDAORepository.refresh(saleItem.getUser());
        saleItem.getDescription();
    }
}
