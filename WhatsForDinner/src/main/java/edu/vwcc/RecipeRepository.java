package edu.vwcc;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findByProteinContainingIgnoreCase(String protein);

    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE LOWER(i) LIKE LOWER(CONCAT('%', :ingredient, '%'))")
    List<Recipe> searchByIngredient(@Param("ingredient") String ingredient);
    
    List<Recipe> findByTagContainingIgnoreCase(String tag);
}