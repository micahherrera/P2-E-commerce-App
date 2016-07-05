package com.example.micahherrera.project2ecommerceapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements ProductViewHolder.ProductListListener{
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private MenuItem item;
    private ProductListFragment products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAssetHelper dbAssetHelper = new DBAssetHelper(MainActivity.this);
        dbAssetHelper.getReadableDatabase();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        addDrawerItems();
        setupDrawer();

        handleIntent(getIntent());

        FragmentManager fm = getSupportFragmentManager();
        products = new ProductListFragment();
        fm.beginTransaction().replace(R.id.productListFragmentMain, products).commit();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_SEARCH);
                switch(position){
                    case 0:
                        intent.putExtra(SearchManager.QUERY, "1");
                        handleIntent(intent);
                        break;
                    case 1:
                        intent.putExtra(SearchManager.QUERY, "2");
                        handleIntent(intent);
                        break;
                    case 2:
                        intent.putExtra(SearchManager.QUERY, "3");
                        handleIntent(intent);
                        break;
                    case 3:
                        intent.putExtra(SearchManager.QUERY, "4");
                        handleIntent(intent);
                        break;
                    case 4:
                        intent.putExtra(SearchManager.QUERY, "5");
                        handleIntent(intent);
                        break;
                    case 5:
                        intent.putExtra(SearchManager.QUERY, "6");
                        handleIntent(intent);
                        break;
                    case 6:
                        intent.putExtra(SearchManager.QUERY, "7");
                        handleIntent(intent);
                        break;
                    case 7:
                        products.cursor = ESQLHelper.getInstance(MainActivity.this).allProducts();
                        products.productListAdapter.changeCursor(products.cursor);
                        products.productListAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        item = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                products.cursor = ESQLHelper.getInstance(MainActivity.this).allProducts();
                products.productListAdapter.changeCursor(products.cursor);
                products.productListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;

    }

    public void handleIntent(Intent intent){
        Log.d("TESTING", "handleIntent: ");
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            switch (query.toLowerCase()) {
                case "alternative":
                    query = "1";
                    break;
                case "indie":
                    query = "2";
                    break;
                case "pop":
                    query = "3";
                    break;
                case "rock":
                    query = "4";
                    break;
                case "hip hop":
                    query = "5";
                    break;
                case "rnb":
                    query = "6";
                    break;
                case "classical":
                    query = "7";
                    break;
                default:
                    break;
            }
            Log.d("TESTING", query);
            products.cursor = ESQLHelper.getInstance(MainActivity.this)
                    .searchArtistAndTitle(query);


            products.productListAdapter.changeCursor(products.cursor);
            products.productListAdapter.notifyDataSetChanged();
        }
    }

    private void addDrawerItems() {

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.drawerMainArray));
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close){

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Genre");
                invalidateOptionsMenu();
            }


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.action_cart:
                showCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
