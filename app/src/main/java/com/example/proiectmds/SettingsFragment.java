package com.example.proiectmds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.proiectmds.services.ClientService;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("dark"))
            {
                boolean switchPref = sharedPreferences.getBoolean(SettingsActivity.DARK_SWITCH,false);
                if (switchPref)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
            if (key.equals("color"))
            {
                if (isAdded()) {
                    String color = sharedPreferences.getString("color", "");
                    Window window = requireActivity().getWindow();
                    switch (color) {
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
                }
            }
            if (key.equals("color2"))
            {
                if (isAdded()) {
                    String color = sharedPreferences.getString("color2", "");
                    Window window = requireActivity().getWindow();
                    switch (color) {
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
            if (key.equals("password") || (key.equals("email")))
            {
                ClientService clientService = new ClientService();
                String email = sharedPreferences.getString("email","");
                String password = sharedPreferences.getString("password","");
                int id = clientService.idByEmail(email);
                clientService.updateCredentials(email,password,id);
            }
        }
    };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        findPreference("bitbucket").setOnPreferenceClickListener(preference -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bitbucket.org/iliecristian1/proiect-mds"));
            startActivity(browserIntent);
            return true;
        });

        findPreference("jira").setOnPreferenceClickListener(preference -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iliecristian.atlassian.net/jira/software/projects/MDS/boards/1"));
            startActivity(browserIntent);
            return true;
        });

        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(listener);


    }
}