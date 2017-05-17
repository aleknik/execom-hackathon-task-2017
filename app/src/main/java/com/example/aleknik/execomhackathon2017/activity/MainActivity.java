package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.adapter.SaleItemAdapter;
import com.example.aleknik.execomhackathon2017.listener.OnItemClickListener;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.preference.UserPreferences_;
import com.example.aleknik.execomhackathon2017.repository.SaleItemDAORepository;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@OptionsMenu(R.menu.main_menu)
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST_CODE = 1;

    private static final int ADD_REQUEST_CODE = 2;

    private static final int ITEM_REQUEST_CODE = 3;

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    FloatingActionButton fab;

    @ViewById
    TextView emptyView;

    @Bean
    SaleItemAdapter saleItemAdapter;

    @Pref
    UserPreferences_ userPreferences;

    @Bean
    UserDAORepository userDAORepository;

    @Bean
    SaleItemDAORepository saleItemDAORepository;

    final Gson gson = new Gson();

    private boolean gridLayout;
    private boolean allItems;

    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager gridLayoutManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem login = menu.findItem(R.id.login);
        MenuItem myItems = menu.findItem(R.id.myItems);
        MenuItem register = menu.findItem(R.id.register);
        if (userPreferences.id().exists()) {
            logout.setVisible(true);
            login.setVisible(false);
            myItems.setVisible(true);
            register.setVisible(false);
            fab.show();
        } else {
            logout.setVisible(false);
            login.setVisible(true);
            myItems.setVisible(false);
            register.setVisible(true);
            fab.hide();
        }
        return true;
    }

    @Click(R.id.fab)
    void addSaleItem() {
        NewSaleItemActivity_.intent(this).startForResult(ADD_REQUEST_CODE);
    }

    @OnActivityResult(ADD_REQUEST_CODE)
    public void onAddSaleItem(int resultCode) {
        if (resultCode == RESULT_OK) {
            showMyItems();
        }
    }

    private void showMyItems() {
        setTitle(getResources().getString(R.string.my_items));
        saleItemAdapter.setItems(saleItemDAORepository.findByUser(userDAORepository.getLoggedInUser()));
        saleItemAdapter.notifyDataSetChanged();
        setEmptyMessage();
        allItems = false;
    }

    private void showAllItems() {
        setTitle(getResources().getString(R.string.all_items));
        saleItemAdapter.setItems(saleItemDAORepository.findAll());
        saleItemAdapter.notifyDataSetChanged();
        setEmptyMessage();
        allItems = true;
    }

    private void setEmptyMessage() {
        if (saleItemAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @AfterViews
    void init() {

        saleItemAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(SaleItem item) {
                goToDetails(item);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        gridLayout = false;
        recyclerView.setAdapter(saleItemAdapter);
        if (userPreferences.id().exists()) {
            fab.show();
            showMyItems();
        } else {
            fab.hide();
            showAllItems();
        }
    }

    void goToDetails(SaleItem item) {
        SaleItemDetailsActivity_.intent(this).extra("item", gson.toJson(item)).startForResult(ITEM_REQUEST_CODE);
    }


    @OnActivityResult(ITEM_REQUEST_CODE)
    public void onDetails(int resultCode) {
        if (resultCode == RESULT_OK) {
            if (allItems)
                showAllItems();
            else
                showMyItems();
        }
    }

    @OptionsItem(R.id.login)
    void login() {
        LoginActivity_.intent(this).startForResult(LOGIN_REQUEST_CODE);
    }

    @OptionsItem(R.id.logout)
    void loguot() {
        userPreferences.id().remove();
        invalidateOptionsMenu();
        showAllItems();
        Toast.makeText(this, "Logout successful.", Toast.LENGTH_LONG).show();
    }

    @OptionsItem(R.id.allItems)
    void showAllItemsCick() {
        showAllItems();
    }

    @OptionsItem(R.id.myItems)
    void showMyItemsClick() {
        showMyItems();

    }

    @OptionsItem(R.id.register)
    void register() {
        RegistrationActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_NO_HISTORY).start();
    }


    @OnActivityResult(LOGIN_REQUEST_CODE)
    public void onLogin(int resultCode) {
        if (resultCode == RESULT_OK) {
            showMyItems();
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (allItems)
            showAllItems();
        else
            showMyItems();
    }

    @OptionsItem(R.id.layoutOption)
    void changeLayout(MenuItem layoutOption) {

        if (gridLayout) {
            recyclerView.setLayoutManager(linearLayoutManager);
            gridLayout = false;
            layoutOption.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_quilt_white_24dp, null));
        } else {
            recyclerView.setLayoutManager(gridLayoutManager);
            gridLayout = true;
            layoutOption.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_stream_white_24dp, null));
        }

    }


}
