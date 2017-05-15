package com.example.aleknik.execomhackathon2017.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.SaleItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.item_view_sale_item)
public class SaleItemItemView extends LinearLayout {

    @ViewById
    TextView name;

    @ViewById
    TextView description;

    public SaleItemItemView(Context context) {
        super(context);
    }

    public void bind(SaleItem item) {
        name.setText(item.getName());
        description.setText(item.getDescription());
    }
}
