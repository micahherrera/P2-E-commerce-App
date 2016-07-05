package com.example.micahherrera.project2ecommerceapp;

import java.util.ArrayList;

/**
 * Created by micahherrera on 6/27/16.
 */
public class ShoppingCartHolder {

    private static ShoppingCartHolder shoppingCartHolder;
    private static ArrayList<Vinyl> shoppingCartList;

    private ShoppingCartHolder(){
        shoppingCartList = new ArrayList<>();
    }

    public static ShoppingCartHolder getInstance(){
        if(shoppingCartHolder == null)
            shoppingCartHolder = new ShoppingCartHolder();
        return shoppingCartHolder;
    }

    public void addToCart(Vinyl vinyl){
        shoppingCartList.add(vinyl);
    }

    public void deleteFromCart(Vinyl vinyl){
        shoppingCartList.remove(shoppingCartList.indexOf(vinyl));
    }

    public void clearCart(){
        shoppingCartHolder = new ShoppingCartHolder();
    }

    public Vinyl getVinyl(int position){
        return shoppingCartList.get(position);
    }

    public int getCount(){
        return shoppingCartList.size();
    }

    public double getPrice(){
        double price = 0;
        for(Vinyl vinyl:shoppingCartList){
            price += vinyl.getPrice();
        }
        return (Math.round(price*100.0)/100.0);
    }

}
