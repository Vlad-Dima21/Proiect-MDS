package com.example.proiectmds;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity {

    public static final String DARK_SWITCH = "dark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String navBarCol = prefs.getString("color","");
        String statusBarCol = prefs.getString("color2","");
        Window window = getWindow();
        switch (navBarCol) {
            case "Blue":
                window.setNavigationBarColor(Color.rgb(116, 169, 219));
                break;
            case "Green":
                window.setNavigationBarColor(Color.rgb(142, 240, 137));
                break;
            case "Black":
                window.setNavigationBarColor(Color.BLACK);
                break;
            case "Gray":
                window.setNavigationBarColor(Color.rgb(184, 184, 184));
                break;
        }
        switch (statusBarCol) {
            case "Blue":
                window.setStatusBarColor(Color.rgb(116, 169, 219));
                break;
            case "Green":
                window.setStatusBarColor(Color.rgb(142, 240, 137));
                break;
            case "Black":
                window.setStatusBarColor(Color.BLACK);
                break;
            case "Gray":
                window.setStatusBarColor(Color.rgb(184, 184, 184));
                break;
        }
    }

}