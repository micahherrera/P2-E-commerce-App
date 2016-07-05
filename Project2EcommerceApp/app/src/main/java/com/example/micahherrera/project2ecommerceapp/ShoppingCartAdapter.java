package com.example.micahherrera.project2ecommerceapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by micahherrera on 6/27/16.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private Context mContext;

    private ProductViewHolder.ProductListListener mListener;


    public ShoppingCartAdapter(Context context, ProductViewHolder.ProductListListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_cart_list_item, parent, false);
        return new ProductViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Vinyl vinyl = ShoppingCartHolder.getInstance().getVinyl(position);
        holder.productTitle.setText(vinyl.getTitle());
        holder.productArtist.setText(vinyl.getName());
        holder.vinyl = vinyl;
        holder.putThePhoto(vinyl.getImageLinkV());
        holder.putThePrice("$" +vinyl.getPrice());

    }

    @Override
    public int getItemCount() {
        return ShoppingCartHolder.getInstance().getCount();
    }







}
