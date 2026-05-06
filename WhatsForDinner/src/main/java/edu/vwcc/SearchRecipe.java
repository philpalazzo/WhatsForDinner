package edu.vwcc;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class SearchRecipe {

	private final RecipeRepository recipeRepository;

	public SearchRecipe(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	private boolean matchesProteinFilter(String recipeProtein, List<String> filters) {
		if (filters.isEmpty() || filters.contains("any")) {
			return true;
		}
		
		if (filters.contains("none")) {
			return recipeProtein == null || recipeProtein.isBlank() || recipeProtein.equals("none");
		}
		
		return filters.contains(recipeProtein);
	}
	

	private String normalize(String s) {
		return s.toLowerCase()
				.replaceAll("[^a-z\\s]", "")
				.trim()
				.replaceAll("\\s+", " ");
	}
	
	private int countIngredientMatches(Recipe recipe, List<String> inputs) {
		List<String> recipeIngredients = Optional.ofNullable(recipe.getIngredients()).orElse(List.of());
		
		int matches = 0;
		
		for (String ing : recipeIngredients) {
			String normIng = normalize(ing);
			
			for (String input : inputs) {
				if (normIng.contains(input)) {
					matches++;
					break;
				}
			}
		}
		return matches;
	}
	
	private List<String> cleanList(List<String> input) {
		if (input == null) return List.of();
		
		return input.stream().map(this::normalize).filter(s -> !s.isBlank()).toList();
	}

	public List<Recipe> searchByIngredientsAndProteins(List<String> ingredients, List<String> proteins) {

	    List<String> cleanedIngredients = cleanList(ingredients);
	    List<String> cleanedProteins = cleanList(proteins);

	    List<RecipeScore> scored = new ArrayList<>();

	    for (Recipe recipe : recipeRepository.findAll()) {

	        String recipeProtein = normalize(recipe.getProtein());

	        if (!matchesProteinFilter(recipeProtein, cleanedProteins)) {
	        	continue;
	        }
	        
	        int matchCount = countIngredientMatches(recipe, cleanedIngredients);

	        if (matchCount > 0 || cleanedIngredients.isEmpty()) {
	        	scored.add(new RecipeScore(recipe, matchCount));
	        }
	    }

	    return scored.stream()
	            .sorted(Comparator.comparingInt(RecipeScore::matches).reversed())
	            .map(RecipeScore::recipe)
	            .toList();
	}
}