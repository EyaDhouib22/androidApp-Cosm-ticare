package com.example.cosmticare.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cosmticare.AppDatabase;
import com.example.cosmticare.R;
import com.example.cosmticare.dao.UserDao;
import com.example.cosmticare.entity.User;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImage;
    private ImageButton buttonAddImage;
    private EditText editTextPrenom, editTextNom, editTextAdresse, editTextEmail, editTextTelephone, editTextMdp;
    private Button buttonSubmit;
    private ImageView eyeIcon;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        profileImage = findViewById(R.id.profileImage);
        buttonAddImage = findViewById(R.id.buttonAddImage);
        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextNom = findViewById(R.id.editTextNom);
        editTextAdresse = findViewById(R.id.editTextAdresse);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelephone = findViewById(R.id.editTextTelephone);
        editTextMdp = findViewById(R.id.editTextMdp);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        eyeIcon = findViewById(R.id.eyeIcon);

        buttonAddImage.setOnClickListener( v -> openGallery());


        buttonSubmit.setOnClickListener(v -> {
            if (validateInputs()) {
                String prenom = editTextPrenom.getText().toString().trim();
                String nom = editTextNom.getText().toString().trim();
                String adresse = editTextAdresse.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String tel = editTextTelephone.getText().toString().trim();
                String mdp = editTextMdp.getText().toString().trim();

                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                    UserDao userDao = db.userDao();

                    if (userDao.findByEmail(email) == null) {
                        userDao.insert(new User(prenom, nom, adresse, email, tel, mdp));
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Compte créé !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, MainActivity.class));
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(this, "Email déjà utilisé", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }
        });

        eyeIcon.setOnClickListener(v -> {
            if (isPasswordVisible) {
                editTextMdp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eyeIcon.setImageResource(R.drawable.ic_eye_closed);
            } else {
                editTextMdp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eyeIcon.setImageResource(R.drawable.ic_eye_open);
            }
            isPasswordVisible = !isPasswordVisible;
            editTextMdp.setSelection(editTextMdp.length());
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur lors de la sélection de l'image", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    private boolean validateInputs() {
        String prenom = editTextPrenom.getText().toString().trim();
        String nom = editTextNom.getText().toString().trim();
        String adresse = editTextAdresse.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String telephone = editTextTelephone.getText().toString().trim();
        String motDePasse = editTextMdp.getText().toString().trim();

        if (prenom.isEmpty()) {
            String message = "Le prénom est requis";
            editTextPrenom.setError(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nom.isEmpty()) {
            String message = "Le nom est requis";
            editTextNom.setError(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (adresse.isEmpty()) {
            String message = "L'adresse est requise";
            editTextAdresse.setError(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            String message = "Veuillez entrer un e-mail valide";
            editTextEmail.setError(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (telephone.isEmpty() || !telephone.matches("[0-9]{8,12}")) {
            String message = "Veuillez entrer un numéro de téléphone valide";
            editTextTelephone.setError(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (motDePasse.isEmpty() || motDePasse.length() < 6) {
            String message = "Le mot de passe doit contenir au moins 6 caractères";
            editTextMdp.setError(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
