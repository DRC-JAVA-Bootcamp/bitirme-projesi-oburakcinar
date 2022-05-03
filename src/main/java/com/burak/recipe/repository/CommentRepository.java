package com.burak.recipe.repository;

import com.burak.recipe.entity.Comment;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByMemberAndRecipe(Member member, Recipe recipe);

    List<Comment> findAllByRecipe(Recipe recipe);

    List<Comment> findAllByMember(Member member);
}
