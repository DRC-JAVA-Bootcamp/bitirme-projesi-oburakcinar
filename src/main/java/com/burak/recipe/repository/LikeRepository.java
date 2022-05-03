package com.burak.recipe.repository;

import com.burak.recipe.entity.Like;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByMemberAndRecipe(Member member, Recipe recipe);

    List<Like> findAllByRecipe(Recipe recipe);

    List<Like> findAllByMember(Member member);
}
