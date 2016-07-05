package com.example.micahherrera.project2ecommerceapp;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by micahherrera on 6/27/16.
 */
public class RecyclerViewAdapter extends RecyclerViewCursorAdapter<ProductViewHolder> {

    private ProductViewHolder.ProductListListener mListener;


    public RecyclerViewAdapter(Cursor cursor, ProductViewHolder.ProductListListener listener) {
        super(cursor);
        mListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(view, mListener);
    }

    @Override
    protected void onBindViewHolder(ProductViewHolder holder, Cursor cursor) {
        String artist = cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.NAME));
        String bio = cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.BIO));
        String desc = cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.DESC));
        double price = cursor.getDouble(cursor.getColumnIndex(ESQLHelper.VinylDatabase.PRICE));
        String title = cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.TITLE));
        String imageLinkV = cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.IMAGE));
        String imageLink = cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.IMAGE));
        boolean inStock = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.INSTOCK)));
        int genreID1 = cursor.getInt(cursor.getColumnIndex(ESQLHelper.VinylDatabase.GENRE1));
        int genreID2 = cursor.getInt(cursor.getColumnIndex(ESQLHelper.VinylDatabase.GENRE2));
        int id = cursor.getInt(0);

        Vinyl vinyl = new Vinyl(id, artist, bio, imageLink, title, desc, price, inStock, imageLinkV,
                genreID1, genreID2);

        holder.productArtist.setText(vinyl.getName());
        if(inStock){
            holder.productPrice.setText("$"+Double.toString(vinyl.getPrice()));
        } else {
            holder.productPrice.setText("Sold Out");
        }
        holder.productTitle.setText(vinyl.getTitle());
        holder.vinyl = vinyl;

        holder.putThePhoto(vinyl.getImageLinkV());




    }



}
