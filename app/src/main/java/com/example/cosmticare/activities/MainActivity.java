package com.example.cosmticare.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cosmticare.AppDatabase;
import com.example.cosmticare.R;
import com.example.cosmticare.dao.UserDao;
import com.example.cosmticare.entity.User;


public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView bienvenueTextView;

    private AppDatabase db;
    private UserDao userDao;

    public static final String PREFS_NAME = "UserSessionPrefs";
    public static final String KEY_USER_EMAIL = "loggedInUserEmail";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        bienvenueTextView = findViewById(R.id.textViewBienvenue);
        bienvenueTextView.setShadowLayer(4f, 2f, 2f, Color.parseColor("#80000000"));

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);

        db = AppDatabase.getInstance(getApplicationContext());
        userDao = db.userDao();


        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim().toLowerCase();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(() -> {
                    User user = userDao.login(email, password);

                    runOnUiThread(() -> {
                        if (user != null) {
                            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_USER_EMAIL, user.getEmail());
                            editor.apply();

                            Toast.makeText(MainActivity.this, "Bienvenue " + user.getPrenom(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).start();
            }
        });

        TextView signUpLink = findViewById(R.id.textViewSignUpLink);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    "SHOPPING_CHANNEL_ID",   // Correction ici : "SHOP+8PING_CHANNEL_ID" => "SHOPPING_CHANNEL_ID"
                    "ShoppingChannel",
                    importance
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {  // Ajout d'une vérification pour éviter un NullPointerException
                notificationManager.createNotificationChannel(channel);
            }
        }
    }







}