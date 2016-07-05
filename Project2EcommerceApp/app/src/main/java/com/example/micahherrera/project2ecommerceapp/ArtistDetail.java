package com.example.micahherrera.project2ecommerceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ArtistDetail extends AppCompatActivity implements ProductViewHolder.ProductListListener{
    ImageView artistImage;
    TextView artistName;
    TextView artistBio;
    int id;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_view_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        artistImage = (ImageView)findViewById(R.id.artistImage);
        artistName = (TextView)findViewById(R.id.artistNameArtistView);
        artistBio = (TextView)findViewById(R.id.artistBio);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt(AlbumDetail.ARTISTID);
        cursor = ESQLHelper.getInstance(this).getArtist(id);
        cursor.moveToFirst();
        artistName.setText(cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.NAME)));
        artistBio.setText(cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.BIO)));

        Picasso.with(this)
                .load("android.resource://com.example.micahherrera.project2ecommerceapp/drawable/"
                        +cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.IMAGE)))
                .resize(145,145)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(artistImage);

        FragmentManager fm = getSupportFragmentManager();
        ProductListFragment products = new ProductListFragment();
        fm.beginTransaction().replace(R.id.artistDetailFragmentFrame, products).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.detail_cart:
                showCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showCart() {
        if(ShoppingCartHolder.getInstance().getCount()>0) {
            FragmentManager fm = getSupportFragmentManager();
            ShoppingCartDialogFragment shoppingCartDialog = new ShoppingCartDialogFragment();
            shoppingCartDialog.show(fm, "shopping_cart_dialog_fragment");
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Your shopping cart is empty!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onProductListClick(Vinyl vinyl) {
        Log.d("TESTING", vinyl.getTitle());
        Bundle bundle = new Bundle();
        bundle.putInt(ProductListFragment.IDIFIER, vinyl.getVinylId());
        Intent intent = new Intent(this, AlbumDetail.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Vinyl vinyl){
        Log.d("TESTING", "money click");
        ShoppingCartHolder.getInstance().addToCart(vinyl);

    }

    @Override
    public void onRemoveFromCartClick(Vinyl vinyl){
        Log.d("TESTING", "remove");
        ShoppingCartHolder.getInstance().deleteFromCart(vinyl);
        ShoppingCartDialogFragment fragment = (ShoppingCartDialogFragment)
                getSupportFragmentManager().findFragmentByTag("shopping_cart_dialog_fragment");
        fragment.updateList();
        fragment.setPriceCart();

    }
}
