package edu.vwcc;

import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

	private final RecipeService recipeService;

	public AppController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/recipes")
	public String recipes(Model model) {
		model.addAttribute("recipes", recipeService.getAllRecipes());
		model.addAttribute("favoriteIds", recipeService.getFavoriteIds());
		return "index";
	}

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "add";
	}

	@PostMapping("/add")
	public String addRecipe(@ModelAttribute Recipe recipe, @RequestParam String ingredientsInput, Model model) {

		String protein = recipeService.normalizeProtein(recipe.getProtein());

		if (!recipeService.isValidProtein(protein)) {
			model.addAttribute("error",
					"Invalid protein type. Allowed: any, beef, chicken, seafood, shrimp, pork, other, none");
			model.addAttribute("recipe", recipe);
			return "add";
		}

		recipeService.saveRecipe(recipe, ingredientsInput);
		return "redirect:/recipes";
	}

	@GetMapping("/favorites")
	public String favorites(Model model) {
		model.addAttribute("recipes", recipeService.getFavorites());
		return "favorites";
	}

	@PostMapping("/favorite/{id}")
	public String addFavorite(@PathVariable int id) {
		recipeService.addFavorite(id);
		return "redirect:/recipes";
	}

	@PostMapping("/favorite/remove/{id}")
	public String removeFavorite(@PathVariable int id) {
		recipeService.removeFavorite(id);
		return "redirect:/favorites";
	}

	@GetMapping("/search")
	public String searchPage(Model model) {
		model.addAttribute("mode", "tag");
		return "search";
	}

	@PostMapping("/search")
	public String search(@RequestParam String mode,
			@RequestParam(required = false) String tag,
			@RequestParam(required = false) String protein,
			@RequestParam(required = false) String ingredientsInput,
			@RequestParam(required = false) String proteinsInput, Model model) {

		List<Recipe> results = new ArrayList<>();

		if (mode.equals("tag")) {
			results = recipeService.searchByTag(tag);
		} else if (mode.equals("protein")) {
			String normalized = recipeService.normalizeProtein(protein);
			results = recipeService.searchByProtein(normalized);
		} else if (mode.equals("ingredients")) {
			List<String> proteins = recipeService.parseList(proteinsInput).stream().map(recipeService::normalizeProtein)
					.toList();
			List<String> ingredients = recipeService.parseList(ingredientsInput);
			results = recipeService.searchByIngredientsAndProteins(ingredients, proteins);
		}

		model.addAttribute("recipes", results);
		model.addAttribute("isEmpty", results.isEmpty());
		model.addAttribute("favoriteIds", recipeService.getFavoriteIds());
		model.addAttribute("mode", mode);
		return "search";
	}

	@GetMapping("/update/{id}")
	public String editForm(@PathVariable int id, Model model) {
		model.addAttribute("recipe", recipeService.getRecipe(id));
		return "update";
	}

	@PostMapping("/update/{id}")
	public String updateRecipe(@PathVariable int id, @ModelAttribute Recipe updated,
			@RequestParam String ingredientsInput) {

		recipeService.updateRecipe(id, updated, ingredientsInput);
		return "redirect:/recipes";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		recipeService.deleteRecipe(id);
		return "redirect:/recipes";
	}
}