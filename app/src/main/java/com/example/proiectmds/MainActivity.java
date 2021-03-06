package com.example.proiectmds;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proiectmds.data.LoginRepository;
import com.example.proiectmds.domain.Client;
import com.example.proiectmds.services.ProductService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.example.proiectmds.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    Button BSelectImage;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_statistics)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean switchDark = prefs.getBoolean("dark",false);
        if (switchDark)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
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

        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("user");
        String password = bundle.getString("pass");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email",user);
        editor.putString("password",password);
        editor.commit();

        NavigationView navigationViewNav = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationViewNav.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        navUsername.setText(prefs.getString("email",""));

        BSelectImage = navigationView.getHeaderView(0).findViewById(R.id.imageButton);
        IVPreviewImage = navigationView.getHeaderView(0).findViewById(R.id.userImage);

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        IVPreviewImage.setImageBitmap(
                                selectedImageBitmap);

                    }
                }
            });

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        NavigationView navigationViewNav = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationViewNav.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        navUsername.setText(prefs.getString("email",""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void goSettings(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}