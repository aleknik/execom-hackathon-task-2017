package com.example.aleknik.execomhackathon2017.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.adapter.SaleItemAdapter;
import com.example.aleknik.execomhackathon2017.model.SaleItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById
    RecyclerView recyclerView;

    @Bean
    SaleItemAdapter saleItemAdapter;

    List<SaleItem> saleItems = new ArrayList<>();

    @AfterViews
    void init() {
        initData();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(saleItemAdapter);

        saleItemAdapter.setItems(saleItems);
    }

    private void initData() {

        for (int i = 0; i < 20; i++)
        {
            saleItems.add(new SaleItem("test" + i, "desc" + i, new Date()));
        }

    }


}
