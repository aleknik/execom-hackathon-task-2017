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

    @DatabaseField(canBeNull = false)
    private double price;

    @DatabaseField(columnName = "user", canBeNull = false, foreign = true)
    private User user;

    @DatabaseField
    private String imagePath;

    public SaleItem() {
    }

    public SaleItem(String name, String description, Date publishDate, double price) {
        this.name = name;
        this.description = description;
        this.publishDate = publishDate;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
