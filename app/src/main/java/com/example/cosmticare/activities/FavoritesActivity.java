package com.example.cosmticare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cosmticare.R;
import com.example.cosmticare.adapters.ProductAdapter;
import com.example.cosmticare.entity.Product;
import com.example.cosmticare.utils.FavoriteUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private ProductAdapter favoritesAdapter;
    private List<Product> favoriteProductsList;
    private TextView textViewEmpty;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
   
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(FavoritesActivity.this, ProductListActivity.class));
                } else if (itemId == R.id.nav_favorites) {
                    Toast.makeText(FavoritesActivity.this, "Déjà sur Mes Favorits", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.nav_account) {
                    startActivity(new Intent(FavoritesActivity.this, AccountActivity.class));
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(FavoritesActivity.this, AboutActivity.class));
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        textViewEmpty = findViewById(R.id.textViewEmptyFavorites);

        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
        favoriteProductsList = new ArrayList<>();

        favoritesAdapter = new ProductAdapter(this, favoriteProductsList, position -> {
            favoriteProductsList.remove(position);
            favoritesAdapter.notifyItemRemoved(position);
            favoritesAdapter.notifyItemRangeChanged(position, favoriteProductsList.size());
            checkIfEmpty();
        });
        recyclerViewFavorites.setAdapter(favoritesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavoriteProducts();
    }
    private void loadFavoriteProducts() {
        favoriteProductsList.clear();
        Set<String> favoriteIds = FavoriteUtils.getFavoriteIds(this);
        if (favoriteIds.isEmpty()) {
            checkIfEmpty();
            return;
        }
        List<Product> allProducts = ProductListActivity.getSampleProducts();
        for (Product product : allProducts) {
            if (favoriteIds.contains(String.valueOf(product.getId()))) {
                favoriteProductsList.add(product);
            }
        }
        favoritesAdapter.notifyDataSetChanged();
        checkIfEmpty();
    }
    private void checkIfEmpty() {
        if (favoriteProductsList.isEmpty()) {
            recyclerViewFavorites.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerViewFavorites.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}