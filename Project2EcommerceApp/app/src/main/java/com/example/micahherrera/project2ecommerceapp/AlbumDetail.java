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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AlbumDetail extends AppCompatActivity implements View.OnClickListener, ProductViewHolder.ProductListListener{
    ImageView albumImage;
    TextView albumTitle;
    TextView artistName;
    TextView desc;
    TextView price;
    Cursor cursor;
    TextView genre12;
    int id;
    public static final String ARTISTID="aid";
    Vinyl thisVinyl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_detail_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        albumImage = (ImageView)findViewById(R.id.albumImageDetailView);
        albumTitle = (TextView)findViewById(R.id.albumAlbumDetailView);
        artistName = (TextView)findViewById(R.id.artistAlbumDetailView);
        desc = (TextView)findViewById(R.id.detailDetailView);
        price = (TextView)findViewById(R.id.priceListButtonDetailView);
        genre12 = (TextView)findViewById(R.id.albumgenre);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt(ProductListFragment.IDIFIER);
        price.setOnClickListener(this);

        Log.d("TESTING", id+"");
        cursor = ESQLHelper.getInstance(this).getVinyl(id);
        cursor.moveToFirst();


        thisVinyl = new Vinyl(id,
                cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.NAME)),
                cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.BIO)),
                cursor.getString(cursor.getColumnIndex(ESQLHelper.ArtistDatabase.IMAGE)),
                cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.TITLE)),
                cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.DESC)),
                cursor.getDouble(cursor.getColumnIndex(ESQLHelper.VinylDatabase.PRICE)),
                true,
                cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.IMAGE)),
                cursor.getInt(cursor.getColumnIndex(ESQLHelper.VinylDatabase.GENRE1)),
                cursor.getInt(cursor.getColumnIndex(ESQLHelper.VinylDatabase.GENRE2)));

        Picasso.with(this)
                .load("android.resource://com.example.micahherrera.project2ecommerceapp/drawable/"
                        +thisVinyl.getImageLinkV())
                .resize(300,300)
                .onlyScaleDown()
                .placeholder(R.drawable.loading)
                .into(albumImage);

        albumTitle.setText(thisVinyl.getTitle());
        artistName.setText(thisVinyl.getName());
        desc.setText(thisVinyl.getDescription());
        price.setText("$" + Double.toString(thisVinyl.getPrice()));

        if(cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.GENRE2))==null){
        genre12.setText(getGenreName(thisVinyl.getGenreID1()));
        }

        else if(cursor.getString(cursor.getColumnIndex(ESQLHelper.VinylDatabase.GENRE2))!=null){
            genre12.setText(getGenreName(thisVinyl.getGenreID1())+"/"
                    +getGenreName(thisVinyl.getGenreID2()));
        }

        artistName.setOnClickListener(this);
        price.setOnClickListener(this);


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
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.artistAlbumDetailView){
            Bundle bundle = new Bundle();
            bundle.putInt(ARTISTID, cursor.getInt(cursor.getColumnIndex(ESQLHelper.VinylDatabase.ARTISTID)));
            Intent intent = new Intent(AlbumDetail.this, ArtistDetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if(v.getId()==R.id.priceListButtonDetailView){
            ShoppingCartHolder.getInstance().addToCart(thisVinyl);
        }

    }

    public String getGenreName(int genreID){
        String genreName;
        switch (genreID){
            case 1:
                genreName = "Alternative";
                break;
            case 2:
                genreName = "Indie";
                break;
            case 3:
                genreName = "Pop";
                break;
            case 4:
                genreName = "Rock";
                break;
            case 5:
                genreName = "Hip Hop";
                break;
            case 6:
                genreName = "RnB";
                break;
            case 7:
                genreName = "Classical";
                break;
            default:
                genreName = "";
                break;

        }
        return genreName;
    }

    @Override
    public void onProductListClick(Vinyl vinyl) {
        Log.d("TESTING", vinyl.getTitle());
        Bundle bundle = new Bundle();
        bundle.putInt(ProductListFragment.IDIFIER, vinyl.getVinylId());
        Intent intent = new Intent(this, AlbumDetail.class);
        intent.putExtras(bundle);
        //cursor.close();
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
