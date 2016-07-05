package com.example.micahherrera.project2ecommerceapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by micahherrera on 6/30/16.
 */
public class ShoppingCartDialogFragment extends DialogFragment implements View.OnClickListener{

    ProductListFragment cart;
    TextView checkout;
    ImageView closeFrag;

    public ShoppingCartDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_cart_dialog_fragment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setLayout(300, 500);

        checkout = (TextView)view.findViewById(R.id.checkoutText);
        checkout.setOnClickListener(this);
        closeFrag = (ImageView)view.findViewById(R.id.closeDialogButton);
        closeFrag.setOnClickListener(this);
        setPriceCart();
        cart = new ProductListFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.shoppingCartFragView, cart).commit();


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.checkoutText) {
            ShoppingCartHolder.getInstance().clearCart();
            cart.shoppingCartAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Congratulations, your products are on their way!", Toast.LENGTH_SHORT).show();
            dismiss();
        }
        else if(v.getId()==R.id.closeDialogButton){
            dismiss();
        }
    }

    @Override
    public void onResume() {
        cart.shoppingCartAdapter.notifyDataSetChanged();
        setPriceCart();
        super.onResume();
    }

    public void setPriceCart(){
        checkout.setText("Checkout: $" +ShoppingCartHolder.getInstance().getPrice());

    }

    public void updateList(){
        cart.shoppingCartAdapter.notifyDataSetChanged();
    }
}
