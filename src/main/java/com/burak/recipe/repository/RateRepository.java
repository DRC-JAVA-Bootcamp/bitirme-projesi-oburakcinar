package com.burak.recipe.repository;

import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Rate;
import com.burak.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findByMemberAndRecipe(Member member, Recipe recipe);

    List<Rate> findAllByRecipe(Recipe recipe);
}
