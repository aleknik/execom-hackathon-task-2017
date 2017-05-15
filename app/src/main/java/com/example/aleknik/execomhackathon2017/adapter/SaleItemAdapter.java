package com.example.aleknik.execomhackathon2017.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.aleknik.execomhackathon2017.generic.RecyclerViewAdapterBase;
import com.example.aleknik.execomhackathon2017.generic.ViewWrapper;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.view.SaleItemItemView;
import com.example.aleknik.execomhackathon2017.view.SaleItemItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class SaleItemAdapter extends RecyclerViewAdapterBase<SaleItem, SaleItemItemView> {

    @RootContext
    Context context;

    @Override
    protected SaleItemItemView onCreateItemView(ViewGroup parent, int viewType) {
        return SaleItemItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<SaleItemItemView> holder, int position) {
        SaleItemItemView view = holder.getView();
        SaleItem person = items.get(position);

        view.bind(person);
    }
}
