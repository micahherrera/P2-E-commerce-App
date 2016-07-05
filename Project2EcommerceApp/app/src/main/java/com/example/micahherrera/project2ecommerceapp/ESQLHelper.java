package com.example.micahherrera.project2ecommerceapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by micahherrera on 6/27/16.
 */
public class ESQLHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = DBAssetHelper.DATABASE_NAME;
    private static final int DATABASE_VERSION = DBAssetHelper.DATABASE_VERSION;


    public static abstract class GenreDatabase implements BaseColumns {
        public static final String TABLE_NAME = "genre";
        public static final String ID="_id";
        public static final String GENRE = "genre_name";
    }

    public static abstract class ArtistDatabase implements BaseColumns{
        public static final String TABLE_NAME = "artist";
        public static final String ID="_id";
        public static final String NAME = "name";
        public static final String BIO = "bio";
        public static final String IMAGE = "imageLink";


    }

    public static abstract class VinylDatabase implements BaseColumns{
        public static final String TABLE_NAME = "vinyl";
        public static final String ID="_id";
        public static final String TITLE="title";
        public static final String ARTISTID = "artistID";
        public static final String DESC = "description";
        public static final String IMAGE = "imageLinkV";
        public static final String GENRE1="genreID1";
        public static final String GENRE2 = "genreID2";
        public static final String INSTOCK = "inStock";
        public static final String PRICE = "price";

    }

    public static abstract class PurchaseDatabase implements BaseColumns{
        public static final String TABLE_NAME = "purchase";
        public static final String ID="_id";
        public static final String PRODUCTS = "products";
        public static final String TOTAL = "total";

    }

    private static ESQLHelper instance;

    private ESQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ESQLHelper getInstance(Context context){
        if(instance==null){
            instance = new ESQLHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }


    // get all products from database
    public Cursor allProducts(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT vinyl._id, %s, %s, %s, %s, %s, %s, %s, %s," +
                "%s, %s, %s FROM %s JOIN %s WHERE %s = artist._id ORDER BY %s",
                VinylDatabase.TITLE,
                VinylDatabase.ARTISTID,
                VinylDatabase.DESC,
                VinylDatabase.GENRE1,
                VinylDatabase.GENRE2,
                VinylDatabase.INSTOCK,
                VinylDatabase.PRICE,
                VinylDatabase.IMAGE,
                ArtistDatabase.NAME,
                ArtistDatabase.BIO,
                ArtistDatabase.IMAGE,
                VinylDatabase.TABLE_NAME,
                ArtistDatabase.TABLE_NAME,
                VinylDatabase.ARTISTID,
                VinylDatabase.TITLE);

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // handle search for artist or title
    public Cursor searchArtistAndTitle(String searchQuery){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s JOIN %s WHERE %s = artist._id " +
                "AND %s LIKE '%%" +
                searchQuery + "%%' OR %s = artist._id AND %s LIKE '%%" + searchQuery + "%%' " +
                "OR %s = artist._id AND %s = '"
                + searchQuery + "' " +
                "OR %s = artist._id AND %s = '" + searchQuery + "'",
                VinylDatabase.TABLE_NAME,
                ArtistDatabase.TABLE_NAME,
                VinylDatabase.ARTISTID,
                VinylDatabase.TITLE,
                VinylDatabase.ARTISTID,
                ArtistDatabase.NAME,
                VinylDatabase.ARTISTID,
                VinylDatabase.GENRE1,
                VinylDatabase.ARTISTID,
                VinylDatabase.GENRE2);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // handle search for genre
    public Cursor searchGenre(int genreID){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s JOIN %s WHERE %s = "+
                genreID+" OR %s = "+genreID,
                VinylDatabase.TABLE_NAME,
                ArtistDatabase.TABLE_NAME,
                VinylDatabase.GENRE1,
                VinylDatabase.GENRE2);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // get artist info for artist detail view
    public Cursor getArtist(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = "+id,
                ArtistDatabase.TABLE_NAME,
                ArtistDatabase.ID);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getArtistWorks(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT vinyl._id, %s, %s, %s, %s, %s, %s, %s, %s," +
                        "%s, %s, %s FROM %s JOIN %s WHERE %s = artist._id AND %s = "+id,
                VinylDatabase.TITLE,
                VinylDatabase.ARTISTID,
                VinylDatabase.DESC,
                VinylDatabase.GENRE1,
                VinylDatabase.GENRE2,
                VinylDatabase.INSTOCK,
                VinylDatabase.PRICE,
                VinylDatabase.IMAGE,
                ArtistDatabase.NAME,
                ArtistDatabase.BIO,
                ArtistDatabase.IMAGE,
                VinylDatabase.TABLE_NAME,
                ArtistDatabase.TABLE_NAME,
                VinylDatabase.ARTISTID,
                VinylDatabase.ARTISTID);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // get purchase history
    public Cursor getPurchaseHistory(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s",
                PurchaseDatabase.TABLE_NAME);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // narrow search by price range
    public Cursor getPriceRange(double maxPrice){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s JOIN %s WHERE %s < "+maxPrice,
                VinylDatabase.TABLE_NAME,
                ArtistDatabase.TABLE_NAME,
                VinylDatabase.PRICE);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }

    public Cursor getVinyl(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM %s JOIN %s WHERE %s = artist._id " +
                "AND vinyl._id = "+id,
                VinylDatabase.TABLE_NAME,
                ArtistDatabase.TABLE_NAME,
                VinylDatabase.ARTISTID);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
