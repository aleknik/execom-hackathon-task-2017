package com.example.aleknik.execomhackathon2017.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class SaleItem {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String description;

    @DatabaseField(canBeNull = false)
    private Date publishDate;

    @DatabaseField(columnName = "user", canBeNull = false, foreign = true)
    private transient User user;
}
