package com.example.micahherrera.project2ecommerceapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by micahherrera on 6/21/16.
 */
public class ProductListFragment extends Fragment {

    public static final String IDIFIER="idifier";
    public RecyclerView productListRecylerView;
    public RecyclerViewAdapter productListAdapter;
    public Cursor cursor;
    public ShoppingCartAdapter shoppingCartAdapter;
    boolean inStock;

    public ProductListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.product_list_fragment, container, false);
        Log.d("TESTING", "onCreateView: "+container);

        ProductViewHolder.ProductListListener myListener = (ProductViewHolder.ProductListListener) getActivity();
        inStock = true;
        productListRecylerView = (RecyclerView) view.findViewById(R.id.recyclerProductList);
        productListRecylerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        if(container.getId()==R.id.productListFragmentMain) {
            cursor = ESQLHelper.getInstance(getContext()).allProducts();
            productListAdapter = new RecyclerViewAdapter(cursor, myListener);
            productListRecylerView.setAdapter(productListAdapter);

        } else if(container.getId()==R.id.artistDetailFragmentFrame){
            Bundle bundle = getActivity().getIntent().getExtras();
            int id = bundle.getInt(AlbumDetail.ARTISTID);
            cursor = ESQLHelper.getInstance(getContext()).getArtistWorks(id);
            productListAdapter = new RecyclerViewAdapter(cursor, myListener);
            productListRecylerView.setAdapter(productListAdapter);

        } else if(container.getId()==R.id.shoppingCartFragView){
            shoppingCartAdapter = new ShoppingCartAdapter(getContext(), myListener);
            productListRecylerView.setAdapter(shoppingCartAdapter);
        }

        return view;
    }



}
