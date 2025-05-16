package com.example.codedex.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class UserManager {
    private static UserManager instance;
    private HashMap<String, String> users;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "currentEmail";

    private UserManager() {
        users = new HashMap<>();
        // Usuario demo
        users.put("test@test.com", "123456");
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public boolean registerUser(String email, String password) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, password);
        return true;
    }

    public boolean loginUser(String email, String password) {
        String storedPassword = users.get(email);
        boolean success = storedPassword != null && storedPassword.equals(password);
        return success;
    }

    public boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean(KEY_LOGGED_IN, false);
        Log.d("UserManager", "isUserLoggedIn: " + loggedIn);
        return loggedIn;
    }
    public void saveLoginState(Context context, boolean isLoggedIn, String email) {
        SharedPreferences preferences = context.getSharedPreferences("com.example.codedex", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("AUTH_OK", isLoggedIn);
        editor.putString("USER_EMAIL", email);
        editor.apply(); // Usamos commit() para escritura inmediata

        Log.d("UserManager", "Estado guardado - Login: " + isLoggedIn + ", Email: " + email);
    }

    public String getCurrentUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_EMAIL, "");
    }

    public void logout(Context context) {
        saveLoginState(context, false, "");
    }

}
