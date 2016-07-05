package com.example.micahherrera.project2ecommerceapp;

/**
 * Created by micahherrera on 6/27/16.
 */
public class Purchase {
    private String productsInList;
    private double total;

    public Purchase(String productsInList, double total) {
        this.productsInList = productsInList;
        this.total = total;
    }

    public String getProductsInList() {
        return productsInList;
    }

    public void setProductsInList(String productsInList) {
        this.productsInList = productsInList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
