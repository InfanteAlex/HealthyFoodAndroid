package com.example.healthyfoodandroid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private RecipeAdapter favoritesAdapter;
    private List<Recipe> favoriteRecipes, filteredRecipes;
    private EditText searchBar;

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

        // Initialize Search Bar
        searchBar = findViewById(R.id.search_bar);

        // Initialize RecyclerView
        favoritesRecyclerView = findViewById(R.id.favorites_recycler_view);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy favorite recipes (Replace with actual data if necessary)
        favoriteRecipes = getFavoriteRecipes();
        filteredRecipes = new ArrayList<>(favoriteRecipes);

        // Initialize Adapter
        favoritesAdapter = new RecipeAdapter(filteredRecipes, recipe -> {
            // Handle recipe click to navigate to RecipeDetailActivity
            startActivity(RecipeDetailActivity.createIntent(this, recipe));
        });

        favoritesRecyclerView.setAdapter(favoritesAdapter);

        // Add TextWatcher to filter results as the user types
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFavorites(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void filterFavorites(String query) {
        filteredRecipes.clear();
        if (query.isEmpty()) {
            filteredRecipes.addAll(favoriteRecipes);
        } else {
            for (Recipe recipe : favoriteRecipes) {
                if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredRecipes.add(recipe);
                }
            }
        }
        favoritesAdapter.notifyDataSetChanged();
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
