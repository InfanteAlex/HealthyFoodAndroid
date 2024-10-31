package com.example.healthyfoodandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recipespage extends AppCompatActivity {

    private EditText recipeSearchBar;
    private RecyclerView featuredRecipesRecycler;
    private RecyclerView topFavoritesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipespage);

        // Initialize views
        recipeSearchBar = findViewById(R.id.recipe_search_bar);
        featuredRecipesRecycler = findViewById(R.id.featured_recipes_recycler);
        topFavoritesRecycler = findViewById(R.id.top_favorites_recycler);

        // Setup search functionality
        recipeSearchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = recipeSearchBar.getText().toString();
            if (!query.isEmpty()) {
                searchRecipes(query);
            } else {
                Toast.makeText(this, "Please enter a search term.", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        // Setup RecyclerViews
        setupRecyclerView(featuredRecipesRecycler, getSampleFeaturedRecipes());
        setupRecyclerView(topFavoritesRecycler, getSampleFavoriteRecipes());
    }

    // Sample data setup for featured recipes
    private List<Recipe> getSampleFeaturedRecipes() {
        List<Recipe> featuredRecipes = new ArrayList<>();
        featuredRecipes.add(new Recipe("Garlic Butter Air Fryer Salmon", "A quick and tasty salmon recipe", R.drawable.recipe_placeholder));
        featuredRecipes.add(new Recipe("Quick Veggie Stir Fry", "Healthy veggies ready in minutes", R.drawable.recipe_placeholder));
        return featuredRecipes;
    }

    // Sample data setup for favorite recipes
    private List<Recipe> getSampleFavoriteRecipes() {
        List<Recipe> favoriteRecipes = new ArrayList<>();
        favoriteRecipes.add(new Recipe("Keto Avocado Salad", "A delicious low-carb salad", R.drawable.recipe_placeholder));
        favoriteRecipes.add(new Recipe("BBQ Chicken Wraps", "Perfect for a quick meal", R.drawable.recipe_placeholder));
        return favoriteRecipes;
    }

    // Setup RecyclerView to accept List<Recipe>
    private void setupRecyclerView(RecyclerView recyclerView, List<Recipe> recipes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecipeAdapter adapter = new RecipeAdapter(recipes);
        recyclerView.setAdapter(adapter);
    }

    // Placeholder search function
    private void searchRecipes(String query) {
        // Future implementation for search functionality
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
    }
}
