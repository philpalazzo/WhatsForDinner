package edu.vwcc;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindByTagTest {

    @Autowired
    RecipeRepository recipeRepository;
    
    @BeforeEach
    void setup() {
        recipeRepository.deleteAll();
        recipeRepository.flush();
    }

    @Test
    void findByTag() {
        Recipe r = new Recipe();
        r.setName("Italian Dish");
        r.setTag("Italian");

        recipeRepository.saveAndFlush(r);

        List<Recipe> results = recipeRepository.findByTagContainingIgnoreCase("italian");

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("Italian Dish", results.get(0).getName());
    }
}
