package com.example.cosmticare.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.cosmticare.AppDatabase;
import com.example.cosmticare.R;
import com.example.cosmticare.dao.UserDao;
import com.example.cosmticare.entity.User;
import com.google.android.material.navigation.NavigationView;
import java.io.File;

public class AccountActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private AppDatabase db;
    private UserDao userDao;
    private ImageView imageProfil;
    private TextView textViewPrenom;
    private TextView textViewNom;
    private TextView textViewEmail;
    private TextView textViewTelephone;
    private TextView textViewAdresse;
    private Button buttonFavoris;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(AccountActivity.this, ProductListActivity.class));
                } else if (item.getItemId() == R.id.nav_favorites) {
                    startActivity(new Intent(AccountActivity.this, FavoritesActivity.class));
                } else if (item.getItemId() == R.id.nav_account) {
                    Toast.makeText(AccountActivity.this, "Vous êtes déjà sur la page Mon Compte", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nav_about) {
                    startActivity(new Intent(AccountActivity.this, FavoritesActivity.class));

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        imageProfil = findViewById(R.id.profileImage);
        textViewPrenom = findViewById(R.id.textViewPrenom);
        textViewNom = findViewById(R.id.textViewNom);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewTelephone = findViewById(R.id.textViewTelephone);
        textViewAdresse = findViewById(R.id.textViewAdresse);
        buttonFavoris = findViewById(R.id.buttonFavoris);
        buttonLogout = findViewById(R.id.buttonLogout);

        imageProfil.setImageResource(R.drawable.ic_user_placeholder);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString(MainActivity.KEY_USER_EMAIL, null);

        db = AppDatabase.getInstance(this);
        userDao = db.userDao();

        if (userEmail != null && !userEmail.isEmpty()) {
            fetchAndDisplayUserData(userEmail);
        } else {
            Toast.makeText(this, "Utilisateur non connecté.", Toast.LENGTH_LONG).show();
        }

        buttonFavoris.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void fetchAndDisplayUserData(String email) {
        new Thread(() -> {
            User user = userDao.findByEmail(email);
            runOnUiThread(() -> {
                if (user != null) {
                    textViewPrenom.setText(user.getPrenom() != null ? user.getPrenom() : "N/A");
                    textViewNom.setText(user.getNom() != null ? user.getNom() : "N/A");
                    textViewEmail.setText(user.getEmail() != null ? user.getEmail() : "N/A");
                    textViewTelephone.setText(user.getTelephone() != null ? user.getTelephone() : "N/A");
                    textViewAdresse.setText(user.getAdresse() != null ? user.getAdresse() : "N/A");

                    // Affichage de l'image de profil
                    String imagePath = user.getImageProfil();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                            imageProfil.setImageBitmap(bitmap);
                        } else {
                            imageProfil.setImageResource(R.drawable.ic_user_placeholder);
                        }
                    } else {
                        imageProfil.setImageResource(R.drawable.ic_user_placeholder);
                    }
                } else {
                    Toast.makeText(AccountActivity.this, "Données utilisateur introuvables pour cet email.", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
