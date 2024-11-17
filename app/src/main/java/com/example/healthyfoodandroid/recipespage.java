package com.example.healthyfoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recipespage extends AppCompatActivity {

    private EditText recipeSearchBar;
    private RecyclerView featuredRecipesRecycler;
    private RecyclerView topFavoritesRecycler;
    private RecipeAdapter featuredAdapter, favoritesAdapter;
    private List<Recipe> featuredRecipesList, topFavoritesList, allRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipespage);

        recipeSearchBar = findViewById(R.id.recipe_search_bar);
        featuredRecipesRecycler = findViewById(R.id.featured_recipes_recycler);
        topFavoritesRecycler = findViewById(R.id.top_favorites_recycler);

        allRecipes = getSampleRecipes();
        featuredRecipesList = new ArrayList<>(allRecipes);
        topFavoritesList = new ArrayList<>(allRecipes);

        setupRecyclerView(featuredRecipesRecycler, featuredRecipesList, "Featured");
        setupRecyclerView(topFavoritesRecycler, topFavoritesList, "Favorites");

        recipeSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRecipes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private List<Recipe> getSampleRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Garlic Butter Salmon", "Quick and delicious salmon", R.drawable.garlic_butter_salmon, "Instructions..."));
        recipes.add(new Recipe("Veggie Stir Fry", "Healthy stir fry", R.drawable.veggie_stir_fry, "Instructions..."));
        recipes.add(new Recipe("Keto Avocado Salad", "Low-carb salad", R.drawable.keto_avocado_salad, "Instructions..."));
        recipes.add(new Recipe("BBQ Chicken Wraps", "Tasty BBQ wraps", R.drawable.bbq_chicken_wraps, "Instructions..."));
        return recipes;
    }

    private void filterRecipes(String query) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(recipe);
            }
        }
        featuredAdapter.updateRecipes(filteredList);
        favoritesAdapter.updateRecipes(filteredList);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Recipe> recipes, String type) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecipeAdapter adapter = new RecipeAdapter(recipes, recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipeTitle", recipe.getTitle());
            intent.putExtra("recipeDescription", recipe.getDescription());
            intent.putExtra("recipeImage", recipe.getImageResourceId());
            intent.putExtra("recipeInstructions", recipe.getInstructions());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        if (type.equals("Featured")) {
            featuredAdapter = adapter;
        } else {
            favoritesAdapter = adapter;
        }
    }
}
