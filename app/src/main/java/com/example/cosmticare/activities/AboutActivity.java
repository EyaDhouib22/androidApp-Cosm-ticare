package com.example.cosmticare.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cosmticare.R;
import com.google.android.material.navigation.NavigationView;

public class AboutActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private static final int ABOUT_CALL_PHONE_PERMISSION_REQUEST_CODE = 456;
    private static final String SUPPORT_PHONE_NUMBER = "29263403";
    private static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

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
                if (item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(AboutActivity.this, ProductListActivity.class));
                } else if (item.getItemId() == R.id.nav_favorites) {
                    startActivity(new Intent(AboutActivity.this, FavoritesActivity.class));
                } else if (item.getItemId() == R.id.nav_account) {
                    startActivity(new Intent(AboutActivity.this, AccountActivity.class));
                } else if (item.getItemId() == R.id.nav_about) {
                    Toast.makeText(AboutActivity.this, "Vous êtes déjà sur la page A propos", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        TextView textViewCall = findViewById(R.id.textViewCall);
        if (textViewCall != null) {
            textViewCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attemptToMakeCall();
                }
            });
        } //else {Log.w(TAG, "TextView with ID 'textViewCall' not found in layout.");}
    }
    private void attemptToMakeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    ABOUT_CALL_PHONE_PERMISSION_REQUEST_CODE);
        } else {
            initiateCall();
        }
    }
    private void initiateCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + SUPPORT_PHONE_NUMBER));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            try {
                startActivity(callIntent);
            } catch (SecurityException e) {
                Log.e(TAG, "SecurityException starting call intent. Is CALL_PHONE permission granted?", e);
                Toast.makeText(this, "Erreur: Permission d'appel refusée.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "Exception starting call intent.", e);
                Toast.makeText(this, "Impossible de passer l'appel. Vérifiez si une application d'appel est installée.", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.w(TAG, "Attempted initiateCall without permission (should not happen).");
            Toast.makeText(this, "Permission d'appel requise.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Always call super
        if (requestCode == ABOUT_CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordée. Lancement de l'appel...", Toast.LENGTH_SHORT).show();
                initiateCall();
            } else {
                Toast.makeText(this, "Permission d'appel refusée. Impossible de passer l'appel.", Toast.LENGTH_LONG).show();
            }
        }
    }
}