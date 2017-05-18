package com.example.aleknik.execomhackathon2017.view;

import android.content.Context;
import android.net.Uri;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

@EViewGroup(R.layout.item_view_sale_item)
public class SaleItemItemView extends RelativeLayout {

    @ViewById
    TextView name;

    @ViewById
    TextView description;

    @ViewById
    TextView price;

    @ViewById
    SimpleDraweeView image;

    public SaleItemItemView(Context context) {
        super(context);
    }

    public void bind(SaleItem item) {
        name.setText(item.getName());
        description.setText(item.getDescription());
        price.setText(String.format(Locale.ENGLISH, "%.2f %s", item.getPrice(), "$"));
        image.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(item.getImagePath()).build());
    }
}
