package com.example.micahherrera.project2ecommerceapp;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DBAssetHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "MYdb";
    public static final int DATABASE_VERSION = 1;

    public DBAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}