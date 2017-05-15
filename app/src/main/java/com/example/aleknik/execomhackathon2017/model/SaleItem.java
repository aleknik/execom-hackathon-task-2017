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
    private User user;

    public SaleItem() {
    }

    public SaleItem(String name, String description, Date publishDate, User user) {
        this.name = name;
        this.description = description;
        this.publishDate = publishDate;
        this.user = user;
    }

    public SaleItem(String name, String description, Date publishDate) {
        this.name = name;
        this.description = description;
        this.publishDate = publishDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
