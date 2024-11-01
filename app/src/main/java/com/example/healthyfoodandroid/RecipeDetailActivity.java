package com.example.healthyfoodandroid;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitle;
    private TextView recipeDescription;
    private ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize views
        recipeTitle = findViewById(R.id.detail_recipe_title);
        recipeDescription = findViewById(R.id.detail_recipe_description);
        recipeImage = findViewById(R.id.detail_recipe_image);

        // Retrieve data from the intent
        String title = getIntent().getStringExtra("recipeTitle");
        String description = getIntent().getStringExtra("recipeDescription");
        int imageResId = getIntent().getIntExtra("recipeImage", R.drawable.recipe_placeholder);
        String instructions = getIntent().getStringExtra("recipeInstructions");

        // Set the data in the views
        recipeTitle.setText(title);
        recipeDescription.setText(description + "\n\n" + instructions);
        recipeImage.setImageResource(imageResId);
    }
}
