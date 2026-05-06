package edu.vwcc;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback
@SpringBootTest
class IngredientsSearchTest {

	@Autowired
	SearchRecipe searchRecipe;

	@Autowired
	RecipeRepository recipeRepository;

    @BeforeEach
    void setup() {
        recipeRepository.deleteAll();
        recipeRepository.flush();
    }
	
	@Test
	void ingredientSearch() {

		Recipe r = new Recipe();
		r.setName("Chicken Dish");
		r.setProtein("chicken");
		r.setIngredients(List.of("chicken", "rice"));

		recipeRepository.saveAndFlush(r);

		List<Recipe> results = searchRecipe.searchByIngredientsAndProteins(List.of("chicken"), List.of("any"));

		assertTrue(results.stream().anyMatch(recipe -> recipe.getName().equals("Chicken Dish")));
	}
}