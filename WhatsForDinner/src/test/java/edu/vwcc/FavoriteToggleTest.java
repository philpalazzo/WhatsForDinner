package edu.vwcc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest

public class FavoriteToggleTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private FaveRecipeService faveRecipeService;
    
    @BeforeEach
    void setup() {
        recipeRepository.deleteAll();
        recipeRepository.flush();
    }

    @Test
    void favoriteToggle() {

        // Create and save recipe
        Recipe r = new Recipe();
        r.setName("Favorites Test");
        recipeRepository.save(r);

        int id = r.getId();

        // Add to favorites using service (real app logic)
        faveRecipeService.addFavorite(id);

        // Verify it exists in favorites list
        boolean exists = faveRecipeService.getFavorites()
                .stream()
                .anyMatch(recipe -> recipe.getId() == id);

        assertTrue(exists, "Recipe should be in favorites");
    }
}