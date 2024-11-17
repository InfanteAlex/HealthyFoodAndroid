package com.example.healthyfoodandroid;

package com.example.healthyfoodandroid;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FavoritesActivityTest {

    private List<Recipe> favoriteRecipes;

    @Before
    public void setUp() {
        // Initialize the list with dummy data
        favoriteRecipes = new ArrayList<>();
        favoriteRecipes.add(new Recipe("Garlic Butter Salmon", "Quick and delicious salmon recipe", 0, "Instructions here..."));
        favoriteRecipes.add(new Recipe("Veggie Stir Fry", "Healthy stir fry with fresh veggies", 0, "Instructions here..."));
    }

    @Test
    public void testDeleteAllFavorites() {
        // Ensure the list is initially populated
        assertFalse(favoriteRecipes.isEmpty());
        assertEquals(2, favoriteRecipes.size());

        // Clear the list
        favoriteRecipes.clear();

        // Assert the list is now empty
        assertTrue(favoriteRecipes.isEmpty());
    }

    @Test
    public void testAddFavoritesBack() {
        // Clear the list first
        favoriteRecipes.clear();
        assertTrue(favoriteRecipes.isEmpty());

        // Add the recipes back
        favoriteRecipes.add(new Recipe("Garlic Butter Salmon", "Quick and delicious salmon recipe", 0, "Instructions here..."));
        favoriteRecipes.add(new Recipe("Veggie Stir Fry", "Healthy stir fry with fresh veggies", 0, "Instructions here..."));

        // Assert the list is repopulated
        assertEquals(2, favoriteRecipes.size());
        assertEquals("Garlic Butter Salmon", favoriteRecipes.get(0).getTitle());
        assertEquals("Veggie Stir Fry", favoriteRecipes.get(1).getTitle());
    }
}

