package edu.vwcc;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class DataLoader implements CommandLineRunner {

	private final RecipeRepository recipeRepository;

	public DataLoader(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void run(String... args) {
		if(recipeRepository.count() > 0)
			return;
		
		recipeRepository.saveAll(List.of(
				recipe("Carmalized Beef",
						"beef",
						"Asian",
						"Step 1\nIn a large skillet over medium-high heat, heat oil. When the oil is hot (rippling, but not smoking), add ground beef broken into small 2-inch clumps and with a little space between each piece. Don’t stir! Wait until the bottoms are dark brown and crisp, about 2 minutes. Flip and brown other side, about 2 minutes. Using the side of a spatula or large spoon, break meat apart into small pieces. Continue cooking until no longer pink, about 1 minute. \r\n"
				+ "Step 2\nAdd ginger and garlic. Continue stirring for 30 seconds.\r\n"
				+ "Step 3\nStir in soy sauce and agave syrup, scraping the dark bits stuck to the bottom of the pan. \r\n"
				+ "Step 4\nRemove skillet off the heat. Drizzle sesame oil and adjust seasoning to taste with salt and pepper. Serve with cooked rice and sprinkle with any of the optional toppings, if you’d like. ",
		List.of("1 Tbsp Vegatable Oil","1 lb Ground Beef","1 Tbsp Ginger","1 Tbsp Garlic","3 Tbsp Soy Sauce","2 Tbsp Sesame Oil","Salt","Pepper","Cooked Rice"))));
		
		recipeRepository.saveAll(List.of(
				recipe("Tuscan Chicken",
						"chicken",
						"Italian",
						"Step 1\nIn a large skillet over medium heat, heat oil. Sprinkle chicken all over with salt, pepper, and oregano. Cook, turning halfway through, until browned on both sides and an instant-read thermometer inserted into thickest part registers 165°, about 8 minutes per side. Transfer to a plate.\r\n"
								+ "Step 2\nIn same skillet over medium heat, melt butter. Stir in garlic and cook, stirring, until fragrant, about 1 minute. Add tomatoes; season with salt and pepper. Cook, stirring, until tomatoes are beginning to burst, about 5 minutes. Add spinach and cook, stirring, until spinach is beginning to wilt, 2 to 3 minutes more.\r\n"
								+ "Step 3\nStir in cream and Parmesan and bring to a simmer. Reduce heat to low and continue to simmer, stirring occasionally, until sauce is slightly reduced, about 3 minutes. Return chicken to skillet and cook, stirring occasionally, until heated through, 5 to 7 minutes.\r\n"
								+ "Step 4\nDivide chicken among plates. Spoon sauce over. Serve with lemon wedges alongside.",
						List.of("1 Tbsp Olive Oil","4 Chicken Breasts", "Salt", "Pepper", "1 tsp Oregano", "3 Tbsp Butter", "3 Garlic Cloves", "1.5 cups Cherry Tomatoes", "3 cups Baby Spinach", ".5 cup Heavy Cream", ".5 cup Parmesan Cheese"))));
		
		recipeRepository.saveAll(List.of(
				recipe("Loaded Nachos",
						"beef",
						"Mexican",
						"Step 1\nPreheat the oven to 350° F. Measure all of the ingredients before beginning, the recipe goes super quick from there!\r\n"
								+ "Step 2\nHeat a large skillet over medium-high heat and add the ground beef. Cook and crumble for 7-10 minutes, or until the beef is brown and cooked through. Drain grease. Add the garlic and taco seasoning and stir in water. Bring to a boil until thickened, then reduce to a simmer. Stir in the refried beans until well combined and heated through. Remove and set aside.\r\n"
								+ "Step 3\nSpread half of the chips in an even layer on a large, light-colored baking sheet. (Mine is 11.2 x 15.7-inches.)\r\n"
								+ "Step 4\nDistribute half of meat mixture in scoops on top and along the sides. Sprinkle with half of the cheese.\r\n"
								+ "Step 5\nTop with the remaining chips, remaining meat mixture, and remaining cheese.\r\n"
								+ "Step 6\nTop with bell peppers, jalapenos, red onions, and black olives.\r\n"
								+ "Step 7\nBake for 15 minutes, or until the cheese is completely melted.\r\n"
								+ "Step 8\nRemove from the oven and sprinkle with tomatoes, diced green onions and cilantro. Top with a generous scoop of guacamole and serve with salsa and sour cream!",
						List.of("1 lb Ground Beef","4 Garlic Cloves",".75 cup Water","1 oz Taco Seasoning","16 oz Refried Beans","14 oz Tortilla Chips","3.75 cups Shredded Cheese",".5 cup diced Bell Peppers","1 sliced Jalepeno Pepper",".25 cup minced Red Onion",".5 cup sliced Black Olives","2 diced Roma Tomatoes","2 sliced Green Onions","3 Tbsp chopped Cilantro"))));
	}
	
	private Recipe recipe(String name, String protein, String tag, String instructions, List<String> ingredients) {
		Recipe r = new Recipe();
		r.setName(name);
		r.setProtein(protein);
		r.setTag(tag);
		r.setInstructions(instructions);
		r.setIngredients(ingredients);
		return r;
	}
}
