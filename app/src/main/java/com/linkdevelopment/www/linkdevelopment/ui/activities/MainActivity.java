package com.linkdevelopment.www.linkdevelopment.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.linkdevelopment.www.linkdevelopment.R;
import com.linkdevelopment.www.linkdevelopment.models.Article;
import com.linkdevelopment.www.linkdevelopment.network.ApiHelper;
import com.linkdevelopment.www.linkdevelopment.network.ConnectivityReceiver;
import com.linkdevelopment.www.linkdevelopment.network.Constants;
import com.linkdevelopment.www.linkdevelopment.ui.adapters.ArticlesAdapter;
import com.linkdevelopment.www.linkdevelopment.utils.Application;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ApiHelper.GetArticles, ArticlesAdapter.OnListClickListener, ConnectivityReceiver.ConnectivityReceiverListener, SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticlesAdapter mAdapter;
    private ProgressBar mPb;
    private List<Article> mArticlesList = new ArrayList<>();
    private TextView mEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar();
        initViews();
        setTitle(getString(R.string.activity_title).toUpperCase());
        checkInternetConnection();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void checkInternetConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            showProgressbar();
            ApiHelper.getArticles(this);
            ApiHelper.setArticleInterface(this);
//            message = getString(R.string.connect_to_internet);
//            color = Color.WHITE;
        } else {
            hideProgressbar();
            message = getString(R.string.not_connected);
            color = Color.RED;
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    private void showProgressbar() {
        mPb.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideProgressbar() {
        mPb.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mPb = (ProgressBar) findViewById(R.id.list_pb);
        mEmptyView = (TextView) findViewById(R.id.emptyView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_explore:
                Toast.makeText(this, R.string.explore, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_live_chat:
                Toast.makeText(this, R.string.live_chat, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_gallery:
                Toast.makeText(this, R.string.gallery, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_wish_list:
                Toast.makeText(this, R.string.wish_list, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_e_magazine:
                Toast.makeText(this, R.string.e_magazine, Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListItemClicked(int ClickedItemPosition) {
        Intent detailsActivity = new Intent(MainActivity.this, DetailsActivity.class);
        detailsActivity.putExtra(Constants.MOVIE, mArticlesList.get(ClickedItemPosition));
        startActivity(detailsActivity);
    }

    @Override
    public void onSuccess(List<Article> articles, Context context) {
        mArticlesList = articles;
        hideProgressbar();
        mAdapter = new ArticlesAdapter(this, articles, this);
        mRecyclerView.setAdapter(mAdapter);
        mArticlesList = articles;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String error) {
        hideProgressbar();
        mEmptyView.setVisibility(View.VISIBLE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
        mEmptyView.setVisibility(View.GONE);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Article> filteredModelList = filter(mArticlesList, newText);
        if (filteredModelList.size() > 0) {
            mAdapter.setFilter(filteredModelList);
            mAdapter.notifyDataSetChanged();
            return true;
        } else {
            Toast.makeText(MainActivity.this, R.string.not_found, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private List<Article> filter(List<Article> models, String query) {
        query = query.toLowerCase();

        final List<Article> filteredModelList = new ArrayList<>();
        for (Article model : models) {
            final String text = model.getAuthor().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);

            }
        }
        return filteredModelList;
    }
}
