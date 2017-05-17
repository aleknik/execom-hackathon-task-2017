package com.example.aleknik.execomhackathon2017.repository;

import com.example.aleknik.execomhackathon2017.database.DatabaseHelper;
import com.example.aleknik.execomhackathon2017.database.dao.SaleItemDAO;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.model.User;

import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EBean
public class SaleItemDAORepository {

    @OrmLiteDao(helper = DatabaseHelper.class)
    SaleItemDAO saleItemDAO;

    public List<SaleItem> findByUser(User user) {
        try {
            final List<SaleItem> saleItems = saleItemDAO.queryBuilder().where().eq("user", user).query();
            return saleItems;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void create(SaleItem saleItem) {
        saleItemDAO.create(saleItem);
    }

    public List<SaleItem> findAll() {
        final List<SaleItem> saleItems = saleItemDAO.queryForAll();
        return saleItems;
    }

    public SaleItem update(SaleItem saleItem) {
        saleItemDAO.update(saleItem);
        return saleItemDAO.queryForId(saleItem.getId());
    }

    public void delete(long id) {
        saleItemDAO.deleteById(id);
    }

}
