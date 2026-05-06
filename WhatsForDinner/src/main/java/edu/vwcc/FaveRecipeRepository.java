package edu.vwcc;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FaveRecipeRepository extends JpaRepository<FaveRecipe, Integer> {
	boolean existsByRecipe_Id(int id);
	long count();
	void deleteByRecipe_Id(int id);
}