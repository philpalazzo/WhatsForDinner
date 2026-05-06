package edu.vwcc;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final SearchRecipe searchService;
    private final FaveRecipeService faveService;

    private static final Set<String> ALLOWED_PROTEINS = Set.of(
            "any", "beef", "chicken", "seafood", "shrimp", "pork",
            "other", "none"
    );

    public RecipeService(RecipeRepository recipeRepository,
                         SearchRecipe searchService,
                         FaveRecipeService faveService) {
        this.recipeRepository = recipeRepository;
        this.searchService = searchService;
        this.faveService = faveService;
    }

    public List<String> parseList(String input) {
        if (input == null || input.isBlank()) return List.of();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    public String normalizeProtein(String protein) {
        if (protein == null || protein.isBlank()) return "none";
        return protein.toLowerCase().trim();
    }

    public Recipe getRecipe(int id) {
        return recipeRepository.findById(id).orElseThrow();
    }

    public boolean isValidProtein(String protein) {
        return ALLOWED_PROTEINS.contains(protein);
    }

    public void saveRecipe(Recipe recipe, String ingredientsInput) {
        recipe.setProtein(normalizeProtein(recipe.getProtein()));
        recipe.setIngredients(parseList(ingredientsInput));
        recipeRepository.save(recipe);
    }

    public void updateRecipe(int id, Recipe updated, String ingredientsInput) {
        Recipe existing = getRecipe(id);

        existing.setName(updated.getName());
        existing.setProtein(normalizeProtein(updated.getProtein()));
        existing.setTag(updated.getTag());
        existing.setInstructions(updated.getInstructions());
        existing.setIngredients(parseList(ingredientsInput));

        recipeRepository.save(existing);
    }

    public void deleteRecipe(int id) {
        faveService.removeFavorite(id);
        recipeRepository.deleteById(id);
    }

    public List<Recipe> searchByTag(String tag) {
        return recipeRepository.findByTagContainingIgnoreCase(tag);
    }

    public List<Recipe> searchByIngredientsAndProteins(List<String> ingredients, List<String> proteins) {
        return searchService.searchByIngredientsAndProteins(ingredients, proteins);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getFavorites() {
        return faveService.getFavorites();
    }
    
    public List<Integer> getFavoriteIds() {
        return faveService.getFavorites()
                .stream()
                .map(Recipe::getId)
                .toList();
    }

    public void addFavorite(int id) {
        faveService.addFavorite(id);
    }

    public void removeFavorite(int id) {
        faveService.removeFavorite(id);
    }
}