package com.example.cosmticare.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "image_profil")
    public String imageProfil;
    @ColumnInfo(name = "prenom")
    public String prenom;
    @ColumnInfo(name = "nom")
    public String nom;
    @ColumnInfo(name = "adresse")
    public String adresse;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "telephone")
    public String telephone;
    @ColumnInfo(name = "password")
    public String password;
    public User(String prenom, String nom, String adresse, String email, String telephone, String password) {
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.imageProfil= imageProfil;
    }

    public int getId() { return id; }
    public String getImageProfil() { return imageProfil; }
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public String getAdresse() { return adresse; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }





}
