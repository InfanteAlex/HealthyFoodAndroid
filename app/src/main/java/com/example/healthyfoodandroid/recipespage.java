package com.example.healthyfoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private RecipeAdapter featuredAdapter, favoritesAdapter;
    private List<Recipe> featuredRecipesList, topFavoritesList, allRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipespage);

        // Initialize views
        recipeSearchBar = findViewById(R.id.recipe_search_bar);
        featuredRecipesRecycler = findViewById(R.id.featured_recipes_recycler);
        topFavoritesRecycler = findViewById(R.id.top_favorites_recycler);

        // Load sample recipes
        allRecipes = getSampleRecipes();
        featuredRecipesList = new ArrayList<>(allRecipes); // Initial list of featured recipes
        topFavoritesList = new ArrayList<>(allRecipes); // Initial list of favorite recipes

        // Setup RecyclerViews with adapters
        setupRecyclerView(featuredRecipesRecycler, featuredRecipesList, "Featured");
        setupRecyclerView(topFavoritesRecycler, topFavoritesList, "Favorites");

        // Setup search functionality
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

    // Sample data for testing
    private List<Recipe> getSampleRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Garlic Butter Salmon",
                "A quick and delicious salmon recipe",
                R.drawable.garlic_butter_salmon,
                "Ingredients:\n- 4 salmon fillets\n- 2 tbsp garlic, minced\n- 3 tbsp butter\n- Salt and pepper to taste\n\n" +
                        "Instructions:\n1. Preheat oven to 375°F (190°C).\n" +
                        "2. Season the salmon fillets with salt and pepper.\n" +
                        "3. In a pan, melt the butter over medium heat and add the garlic. Sauté until fragrant.\n" +
                        "4. Pour garlic butter over the salmon and bake for 15-20 minutes.\n" +
                        "5. Serve warm with your favorite sides."
        ));

        recipes.add(new Recipe("Veggie Stir Fry",
                "Healthy stir fry with fresh veggies",
                R.drawable.veggie_stir_fry,
                "Ingredients:\n- 1 cup broccoli\n- 1 cup bell peppers, sliced\n- 1/2 cup carrots, sliced\n- 2 tbsp soy sauce\n" +
                        "- 1 tbsp olive oil\n\n" +
                        "Instructions:\n1. Heat olive oil in a large pan over medium-high heat.\n" +
                        "2. Add broccoli, bell peppers, and carrots.\n" +
                        "3. Stir-fry for 5-7 minutes until tender-crisp.\n" +
                        "4. Add soy sauce and mix well.\n" +
                        "5. Serve immediately as a side or main dish."
        ));

        recipes.add(new Recipe("Keto Avocado Salad",
                "Low-carb avocado salad",
                R.drawable.keto_avocado_salad,
                "Ingredients:\n- 2 avocados, diced\n- 1 cup cherry tomatoes, halved\n- 1/4 cup red onion, sliced\n" +
                        "- 2 tbsp olive oil\n- Salt and pepper to taste\n\n" +
                        "Instructions:\n1. In a bowl, combine avocados, cherry tomatoes, and red onion.\n" +
                        "2. Drizzle olive oil over the salad and season with salt and pepper.\n" +
                        "3. Toss gently to combine.\n" +
                        "4. Serve fresh and enjoy a healthy, keto-friendly meal."
        ));

        recipes.add(new Recipe("BBQ Chicken Wraps",
                "Easy BBQ chicken wraps",
                R.drawable.bbq_chicken_wraps,
                "Ingredients:\n- 2 cups shredded chicken\n- 1/2 cup BBQ sauce\n- 4 large tortillas\n- 1 cup shredded lettuce\n" +
                        "- 1/2 cup shredded cheese\n\n" +
                        "Instructions:\n1. In a bowl, mix shredded chicken with BBQ sauce.\n" +
                        "2. Lay out the tortillas and evenly distribute the chicken mixture.\n" +
                        "3. Top with shredded lettuce and cheese.\n" +
                        "4. Roll up each tortilla and cut in half.\n" +
                        "5. Serve warm or cold as a delicious, easy-to-make wrap."
        ));

        return recipes;
    }

    // Filter recipes based on the search query
    private void filterRecipes(String query) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(recipe);
            }
        }
        // Update the adapters with filtered lists
        featuredAdapter.updateRecipes(filteredList);
        favoritesAdapter.updateRecipes(filteredList);
    }

    // Setup RecyclerView with the adapter
    private void setupRecyclerView(RecyclerView recyclerView, List<Recipe> recipes, String type) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecipeAdapter adapter = new RecipeAdapter(recipes, recipe -> {
            // Navigate to recipe detail page
            Intent intent = new Intent(recipespage.this, RecipeDetailActivity.class);
            intent.putExtra("recipeTitle", recipe.getTitle());
            intent.putExtra("recipeDescription", recipe.getDescription());
            intent.putExtra("recipeImage", recipe.getImageResourceId());
            intent.putExtra("recipeInstructions", recipe.getInstructions());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // Assign adapter based on type for search updates
        if (type.equals("Featured")) {
            featuredAdapter = adapter;
        } else {
            favoritesAdapter = adapter;
        }
    }
}
