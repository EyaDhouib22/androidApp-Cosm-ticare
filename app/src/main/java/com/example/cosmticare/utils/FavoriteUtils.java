package com.example.cosmticare.utils;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FavoriteUtils {

    private static final String PREFS_NAME = "FavoritePrefs";
    private static final String FAVORITE_IDS_KEY = "favoriteProductIds";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public static Set<String> getFavoriteIds(Context context) {
        return new HashSet<>(getPrefs(context).getStringSet(FAVORITE_IDS_KEY, Collections.emptySet()));
    }
    public static boolean isFavorite(Context context, long productId) {
        Set<String> favorites = getFavoriteIds(context);
        return favorites.contains(String.valueOf(productId));
    }
    public static void addFavorite(Context context, long productId) {
        SharedPreferences prefs = getPrefs(context);
        Set<String> favorites = new HashSet<>(prefs.getStringSet(FAVORITE_IDS_KEY, Collections.emptySet()));
        favorites.add(String.valueOf(productId));
        prefs.edit().putStringSet(FAVORITE_IDS_KEY, favorites).apply();
    }

    public static void removeFavorite(Context context, long productId) {
        SharedPreferences prefs = getPrefs(context);
        Set<String> favorites = new HashSet<>(prefs.getStringSet(FAVORITE_IDS_KEY, Collections.emptySet())); // Get a mutable copy
        if (favorites.contains(String.valueOf(productId))) {
            favorites.remove(String.valueOf(productId));
            prefs.edit().putStringSet(FAVORITE_IDS_KEY, favorites).apply();
        }
    }

    public static boolean toggleFavorite(Context context, long productId) {
        if (isFavorite(context, productId)) {
            removeFavorite(context, productId);
            return false; // not favorite
        } else {
            addFavorite(context, productId);
            return true; //  favorite
        }
    }
}