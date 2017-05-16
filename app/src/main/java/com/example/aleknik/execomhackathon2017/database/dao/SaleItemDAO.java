package com.example.aleknik.execomhackathon2017.database.dao;

import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class SaleItemDAO extends RuntimeExceptionDao<SaleItem, Long> {

    public SaleItemDAO(Dao<SaleItem, Long> dao) {
        super(dao);
    }
}
