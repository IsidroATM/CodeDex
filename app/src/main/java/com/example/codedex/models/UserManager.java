package com.example.codedex.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private HashMap<String, String> users;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "currentEmail";

    public void init(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String email = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                String password = (String) value;
                users.put(email, password);
            }
        }
    }

    private UserManager() {
        users = new HashMap<>();
        // Solo cargamos los usuarios al crearse la instancia, usando un método
    }


    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public boolean registerUser(String email, String password, Context context) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, password);
        saveUserToPreferences(email, password, context);
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
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }


    public String getCurrentUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_EMAIL, "");
    }

    public void logout(Context context) {
        saveLoginState(context, false, "");
    }

    private void saveUserToPreferences(String email, String password, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(email, password);
        editor.apply();
    }

}
