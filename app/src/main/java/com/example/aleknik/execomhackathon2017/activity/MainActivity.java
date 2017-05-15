package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.adapter.SaleItemAdapter;
import com.example.aleknik.execomhackathon2017.model.SaleItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@OptionsMenu(R.menu.main_menu)
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById
    RecyclerView recyclerView;

    @Bean
    SaleItemAdapter saleItemAdapter;

    private static final int LOGIN_REQUEST_CODE = 1;

    List<SaleItem> saleItems = new ArrayList<>();

    private boolean gridLayout;

    private boolean logedIn = false;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem login = menu.findItem(R.id.login);
        if (logedIn) {
            logout.setVisible(true);
            login.setVisible(false);
        } else {
            logout.setVisible(false);
            login.setVisible(true);
        }
        return true;
    }


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

    @OptionsItem(R.id.login)
    void login() {
        LoginActivity_.intent(this).startForResult(LOGIN_REQUEST_CODE);
    }

    @OnActivityResult(LOGIN_REQUEST_CODE)
    public void onLogin(Intent data, int resultCode) {
        if (resultCode == RESULT_OK) {
        }
    }


    @OptionsItem(R.id.layoutOption)
    void changeLayout(MenuItem layoutOption) {

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
