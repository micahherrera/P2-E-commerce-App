package com.example.micahherrera.project2ecommerceapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by micahherrera on 6/28/16.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    private ProductListListener mListener;

    TextView productArtist;
    TextView productTitle;
    TextView productPrice;
    ImageView productPhoto;
    Vinyl vinyl;
    TextView productPriceCart;

    ProductViewHolder(View itemView, ProductListListener listener)
    {
        super(itemView);
        mListener = listener;
        itemView.setOnClickListener(this);
        Log.d("TESTING", "ProductViewHolder: "+itemView.getId());
        productPrice = (TextView) itemView.findViewById(R.id.priceListButtonTextView);
        productPrice.setOnClickListener(this);
        productPhoto = (ImageView) itemView.findViewById(R.id.imageViewAlbumList);
        productTitle = (TextView) itemView.findViewById(R.id.titleList);
        productArtist = (TextView) itemView.findViewById(R.id.artistList);
        if(itemView.getId()==R.id.card_view_shopping_cart){
            productPriceCart = (TextView)itemView.findViewById(R.id.priceListCart);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.priceListButtonTextView && productPrice.getText().equals("Remove")){
            mListener.onRemoveFromCartClick(vinyl);
        }
        else if(v.getId()== R.id.priceListButtonTextView){
            mListener.onAddToCartClick(vinyl);

        } else {

            mListener.onProductListClick(vinyl);
        }
    }

    public interface ProductListListener {

        void onProductListClick(Vinyl vinyl);
        void onAddToCartClick(Vinyl vinyl);
        void onRemoveFromCartClick(Vinyl vinyl);
    }

    public void putThePhoto(String ur){
        Picasso.with(itemView.getContext())
                //.load(productPhotoUR)
                .load("android.resource://com.example.micahherrera.project2ecommerceapp/drawable/"+ur)
                .resize(145, 145)
                .onlyScaleDown()
                .placeholder(R.drawable.loading)
                .into(productPhoto);
    }

    public void putThePrice(String price){
        productPriceCart.setText(price);
    }


}