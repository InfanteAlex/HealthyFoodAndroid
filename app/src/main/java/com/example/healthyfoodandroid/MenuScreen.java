package com.example.healthyfoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        // Apply edge-to-edge UI
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setNavigationBarColor(android.graphics.Color.TRANSPARENT);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        ImageView imgButton = findViewById(R.id.recipes);
        imgButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(MenuScreen.this, recipespage.class));
        });

        ImageView finderButton = findViewById(R.id.finder);
        finderButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(MenuScreen.this, finderpage.class));
        });

        ImageView bankButton = findViewById(R.id.banks);
        bankButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(MenuScreen.this, bank.class));
        });

        ImageView favoritesButton = findViewById(R.id.favs);
        favoritesButton.setOnClickListener(view -> {
            startActivity(new Intent(MenuScreen.this, FavoritesActivity.class));
        });

        ImageView settingsButton = findViewById(R.id.settings);
        settingsButton.setOnClickListener(view -> {
            startActivity(new Intent(MenuScreen.this, settingsActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
