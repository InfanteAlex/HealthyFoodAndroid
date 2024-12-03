package com.example.healthyfoodandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private RecipeAdapter favoritesAdapter;
    private List<Recipe> favoriteRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Favorites");
        }

        // Initialize RecyclerView
        favoritesRecyclerView = findViewById(R.id.favorites_recycler_view);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy favorite recipes (Replace with actual data if necessary)
        favoriteRecipes = getFavoriteRecipes();

        // Initialize Adapter
        favoritesAdapter = new RecipeAdapter(favoriteRecipes, recipe -> {
            // Handle recipe click to navigate to RecipeDetailActivity
            startActivity(RecipeDetailActivity.createIntent(this, recipe));
        });

        favoritesRecyclerView.setAdapter(favoritesAdapter);
    }

    // Dummy favorite recipes
    private List<Recipe> getFavoriteRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Garlic Butter Salmon", "Quick and delicious salmon recipe", R.drawable.garlic_butter_salmon, "Instructions here..."));
        recipes.add(new Recipe("Veggie Stir Fry", "Healthy stir fry with fresh veggies", R.drawable.veggie_stir_fry, "Instructions here..."));
        return recipes;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button action
        onBackPressed();
        return true;
    }
}
