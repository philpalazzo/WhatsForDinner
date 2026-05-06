package edu.vwcc;

import jakarta.persistence.*;

@Entity
public class FaveRecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Recipe recipe;

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public Recipe getRecipe() { return recipe; }
	public void setRecipe(Recipe recipe) { this.recipe = recipe; }
}