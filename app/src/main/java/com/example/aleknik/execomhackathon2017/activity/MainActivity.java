package com.example.aleknik.execomhackathon2017.activity;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.adapter.SaleItemAdapter;
import com.example.aleknik.execomhackathon2017.model.SaleItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@OptionsMenu(R.menu.layout_menu)
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById
    RecyclerView recyclerView;

    @Bean
    SaleItemAdapter saleItemAdapter;

    List<SaleItem> saleItems = new ArrayList<>();

    private boolean gridLayout;

    @OptionsMenuItem
    MenuItem layoutOption;

    @AfterViews
    void init() {
        initData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        gridLayout = false;
        recyclerView.setAdapter(saleItemAdapter);

        saleItemAdapter.setItems(saleItems);
    }

    private void initData() {

        for (int i = 0; i < 20; i++) {
            saleItems.add(new SaleItem("test" + i, "desc" + i, new Date()));
        }

    }

    @OptionsItem(R.id.layoutOption)
    void changeLayout() {

        if (gridLayout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false));
            gridLayout = false;
            layoutOption.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_column_white_24dp, null));

        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            gridLayout = true;
            layoutOption.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_stream_white_24dp, null));
        }

    }


}
