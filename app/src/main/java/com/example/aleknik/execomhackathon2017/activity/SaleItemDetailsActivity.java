package com.example.aleknik.execomhackathon2017.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.preference.UserPreferences_;
import com.example.aleknik.execomhackathon2017.repository.SaleItemDAORepository;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Locale;

@OptionsMenu(R.menu.item_menu)
@EActivity(R.layout.activity_sale_item_details)
public class SaleItemDetailsActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST_CODE = 1;

    @Extra
    String item;

    @Bean
    UserDAORepository userDAORepository;

    @ViewById
    TextView name;

    @ViewById
    TextView description;

    @ViewById
    TextView price;

    @ViewById
    TextView sellerContact;

    @Bean
    SaleItemDAORepository saleItemDAORepository;

    private final Gson gson = new Gson();

    private SaleItem saleItem;

    @Pref
    UserPreferences_ userPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem edit = menu.findItem(R.id.edit);
        MenuItem delete = menu.findItem(R.id.delete);
        if (userPreferences.id().exists() &&
                saleItem.getUser().getId() == userPreferences.id().get()) {
            edit.setVisible(true);
            delete.setVisible(true);
        } else {
            edit.setVisible(false);
            delete.setVisible(false);
        }
        return true;
    }


    @AfterViews
    void init() {
        saleItem = gson.fromJson(item, SaleItem.class);
        userDAORepository.refresh(saleItem.getUser());
        initData();
    }

    void initData() {
        name.setText(saleItem.getName());
        description.setText(saleItem.getDescription());
        price.setText(String.format(Locale.ENGLISH, "%.2f $", saleItem.getPrice()));
        sellerContact.setText(saleItem.getUser().getContact());
    }

    @OptionsItem(R.id.edit)
    void edit() {
        SaleItemEditActivity_.intent(this).extra("item", gson.toJson(saleItem)).startForResult(EDIT_REQUEST_CODE);
    }


    @OnActivityResult(EDIT_REQUEST_CODE)
    public void onEdit(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            saleItem = gson.fromJson(data.getStringExtra("saleItem"), SaleItem.class);
            initData();
        }
    }

    @OptionsItem(R.id.delete)
    void delete() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        saleItemDAORepository.delete(saleItem.getId());
                        setResult(RESULT_OK);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
