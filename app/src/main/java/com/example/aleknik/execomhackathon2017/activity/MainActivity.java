package com.example.aleknik.execomhackathon2017.activity;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.adapter.SaleItemAdapter;
import com.example.aleknik.execomhackathon2017.database.repository.UserDAORepository;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.preference.UserPreferences_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

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

    @Pref
    UserPreferences_ userPreferences;

    @Bean
    UserDAORepository userDAORepository;

    private static final int LOGIN_REQUEST_CODE = 1;

    List<SaleItem> saleItems = new ArrayList<>();

    private boolean gridLayout;

    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager gridLayoutManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem login = menu.findItem(R.id.login);
        if (userPreferences.id().exists()) {
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
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(linearLayoutManager);
        gridLayout = false;
        recyclerView.setAdapter(saleItemAdapter);

        saleItemAdapter.setItems(saleItems);
    }

    private void initData() {

        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0)
                saleItems.add(new SaleItem("test" + i, "desc as das d asas sad as" +
                        "asdsadas asd as asd " +
                        " das da das as d d sad asdsd as da sdasd as sd  as da sd asd as da sd sa da d" + i, new Date()));
            else if (i % 3 == 1)
                saleItems.add(new SaleItem("test" + i, "desc as dassad as d a" + i, new Date()));
            else
                saleItems.add(new SaleItem("test" + i, "desc assad  sad as sad ad  dassad as d a" + i, new Date()));
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
        Toast.makeText(this, "Logout successful.", Toast.LENGTH_LONG).show();
    }

    @OnActivityResult(LOGIN_REQUEST_CODE)
    public void onLogin(int resultCode) {
        if (resultCode == RESULT_OK) {
            invalidateOptionsMenu();
        }
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
