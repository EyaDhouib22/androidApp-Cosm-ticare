package com.example.cosmticare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.cosmticare.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cosmticare.adapters.ProductAdapter;
import com.example.cosmticare.entity.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private ProductAdapter adapter;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

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
                    Toast.makeText(ProductListActivity.this, "Déjà sur l'accueil", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.nav_favorites) {
                    startActivity(new Intent(ProductListActivity.this, FavoritesActivity.class));
                } else if (itemId == R.id.nav_account) {
                    startActivity(new Intent(ProductListActivity.this, AccountActivity.class));
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(ProductListActivity.this, AboutActivity.class));
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        products = getSampleProducts();

        adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter);
    }

    public static List<Product> getSampleProducts() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(1L, R.drawable.ic_1, "Crème Hydratante Visage - Aloe Vera", "25 TND", "Disponible", "Hydrate intensément la peau..."));
        list.add(new Product(2L, R.drawable.ic_2, "Sérum Anti-âge - Acide Hyaluronique", "45 TND", "Disponible", "Sérum anti-âge puissant..."));
        list.add(new Product(3L, R.drawable.ic_3, "Gel Nettoyant Visage - Charbon Actif", "18 TND", "Indisponible", "Nettoyant quotidien au charbon..."));
        list.add(new Product(4L, R.drawable.ic_4, "Crème Solaire SPF 50 - Protection Haute", "35 TND", "Disponible", "Crème solaire avec une protection..."));
        list.add(new Product(5L, R.drawable.ic_5, "Shampooing Revitalisant - Aloe Vera et Kératine", "28 TND", "Disponible", "Shampooing revitalisant aux extraits..."));
        list.add(new Product(6L, R.drawable.ic_6, "Baume à Lèvres Nourrissant - Beurre de Karité", "15 TND", "Disponible", "Baume à lèvres nourrissant..."));
        list.add(new Product(7L, R.drawable.ic_7, "Gel Douche Hydratant - Aloe Vera et Vitamine E", "20 TND", "Disponible", "Gel douche hydratant à l'aloe vera..."));
        list.add(new Product(8L, R.drawable.ic_8, "Crème Anti-Âge - Collagène et Acide Hyaluronique", "45 TND", "Disponible", "Crème anti-âge riche en collagène..."));
        list.add(new Product(9L, R.drawable.ic_9, "Lotion Hydratante - Rose et Camomille", "30 TND", "Disponible", "Lotion hydratante à la rose..."));
        list.add(new Product(10L, R.drawable.ic_10, "Crème Corporelle Nourrissante - Beurre de Cacao", "38 TND", "Disponible", "Crème corporelle nourrissante..."));
        list.add(new Product(11L, R.drawable.ic_11, "Shampoing Revitalisant - Huile de Jojoba et Keratine", "25 TND", "Disponible", "Shampoing revitalisant à l'huile..."));
        list.add(new Product(12L, R.drawable.ic_12, "Baume à Lèvres Nourrissant - Beurre de Karité", "10 TND", "Disponible", "Baume à lèvres nourrissant au beurre..."));
        list.add(new Product(13L, R.drawable.ic_13, "Crème Visage Éclat - Vitamine C et Acide Hyaluronique", "35 TND", "Disponible", "Crème visage éclat à la vitamine C..."));
        list.add(new Product(14L, R.drawable.ic_14, "Déodorant Roll-On - Aloe Vera", "15 TND", "Disponible", "Déodorant Roll-On à l'aloe vera..."));
        list.add(new Product(15L, R.drawable.ic_15, "Crème pour les Mains - Beurre de Karité et Miel", "18 TND", "Disponible", "Crème pour les mains nourrissante..."));
        return list;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isChangingConfigurations()) {
            sendGoodbyeNotification();
        }
    }
    private void sendGoodbyeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "SHOPPING_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_diss)
                .setContentTitle("🎁 Remise spéciale rien que pour vous !")
                .setContentText("Profitez de 10% de réduction sur votre prochain achat. Cliquez pour en profiter !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1001, builder.build());
    }



}