package edu.vwcc;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FaveRecipeService {
	
	private static final int MAX_FAVORITES = 5;

    private final FaveRecipeRepository faveRecipeRepository;
    private final RecipeRepository recipeRepository;

    public FaveRecipeService(FaveRecipeRepository faveRecipeRepository, RecipeRepository recipeRepository) {
        this.faveRecipeRepository = faveRecipeRepository;
        this.recipeRepository = recipeRepository;
    }
    
    private void enforceLimit() {
    	if(faveRecipeRepository.count() >= MAX_FAVORITES)
    		throw new IllegalStateException("Max 5 favorites allowed");
    }
    
    private Recipe getRecipe(int recipeId) {
    	return recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe no found"));
    }

    public void addFavorite(int recipeId) {

        if (faveRecipeRepository.existsByRecipe_Id(recipeId))
            return;
        
        enforceLimit();

        Recipe recipe = getRecipe(recipeId);

        FaveRecipe fav = new FaveRecipe();
        fav.setRecipe(recipe);

        faveRecipeRepository.save(fav);
    }

    public void removeFavorite(int recipeId) {
    	faveRecipeRepository.deleteByRecipe_Id(recipeId);
    }

    public List<Recipe> getFavorites() {
        return faveRecipeRepository.findAll()
                .stream()
                .map(FaveRecipe::getRecipe)
                .toList();
    }
}