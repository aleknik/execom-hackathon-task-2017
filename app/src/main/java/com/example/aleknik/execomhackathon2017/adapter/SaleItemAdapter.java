package com.example.aleknik.execomhackathon2017.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleknik.execomhackathon2017.generic.RecyclerViewAdapterBase;
import com.example.aleknik.execomhackathon2017.generic.ViewWrapper;
import com.example.aleknik.execomhackathon2017.listener.OnItemClickListener;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.view.SaleItemItemView;
import com.example.aleknik.execomhackathon2017.view.SaleItemItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class SaleItemAdapter extends RecyclerViewAdapterBase<SaleItem, SaleItemItemView> {

    @RootContext
    Context context;
    private OnItemClickListener listener;

    @Override
    protected SaleItemItemView onCreateItemView(ViewGroup parent, int viewType) {
        return SaleItemItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<SaleItemItemView> holder, int position) {
        SaleItemItemView view = holder.getView();
        final SaleItem item = items.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
        view.bind(item);
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
