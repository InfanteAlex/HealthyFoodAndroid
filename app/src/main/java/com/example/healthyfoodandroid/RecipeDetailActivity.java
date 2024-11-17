package com.example.healthyfoodandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_TITLE = "extra_recipe_title";
    public static final String EXTRA_RECIPE_DESCRIPTION = "extra_recipe_description";
    public static final String EXTRA_RECIPE_INSTRUCTIONS = "extra_recipe_instructions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get data from intent
        String title = getIntent().getStringExtra(EXTRA_RECIPE_TITLE);
        String description = getIntent().getStringExtra(EXTRA_RECIPE_DESCRIPTION);
        String instructions = getIntent().getStringExtra(EXTRA_RECIPE_INSTRUCTIONS);

        // Bind data to views
        TextView titleTextView = findViewById(R.id.recipe_detail_title);
        TextView descriptionTextView = findViewById(R.id.recipe_detail_description);
        TextView instructionsTextView = findViewById(R.id.recipe_detail_instructions);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        instructionsTextView.setText(instructions);
    }

    // Create an Intent to start this activity
    public static Intent createIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_TITLE, recipe.getTitle());
        intent.putExtra(EXTRA_RECIPE_DESCRIPTION, recipe.getDescription());
        intent.putExtra(EXTRA_RECIPE_INSTRUCTIONS, recipe.getInstructions());
        return intent;
    }
}
