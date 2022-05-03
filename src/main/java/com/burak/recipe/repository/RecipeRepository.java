package com.burak.recipe.repository;

import com.burak.recipe.entity.FoodCategoryEnum;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByFoodCategory(FoodCategoryEnum foodCategoryEnum);

    List<Recipe> findAllByMember(Member member);
}
