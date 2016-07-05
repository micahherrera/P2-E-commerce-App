package com.example.micahherrera.project2ecommerceapp;

/**
 * Created by micahherrera on 6/27/16.
 */
public class Vinyl extends Artist {
    private String title;
    private String description;
    private double price;
    private boolean inStock;
    private String imageLinkV;
    private int genreID1;
    private int genreID2;
    private int id;


    public Vinyl(int id, String name, String bio, String imageLink, String title,
                 String description, double price, boolean inStock,
                 String imageLinkV, int genreID1, int genreID2) {
        super(name, bio, imageLink);
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.inStock = inStock;
        this.imageLinkV = imageLinkV;
        this.genreID1 = genreID1;
        this.genreID2 = genreID2;
    }

    public int getVinylId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getImageLinkV() {
        return imageLinkV;
    }

    public void setImageLinkV(String imageLinkV) {
        this.imageLinkV = imageLinkV;
    }

    public int getGenreID1() {
        return genreID1;
    }

    public void setGenreID1(int genreID1) {
        this.genreID1 = genreID1;
    }

    public int getGenreID2() {
        return genreID2;
    }

    public void setGenreID2(int genreID2) {
        this.genreID2 = genreID2;
    }
}
