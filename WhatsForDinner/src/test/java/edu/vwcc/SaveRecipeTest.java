package edu.vwcc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class SaveRecipeTest {

	@Autowired
	RecipeRepository recipeRepository;
	FaveRecipeRepository faveRecipeRepository;
	
	@Test
	void SaveRecipe() {
		Recipe r = new Recipe();
		r.setName("Test Recipe");
		
		recipeRepository.save(r);
		
		assertTrue(recipeRepository.findAll().size() > 0);
	}
}
