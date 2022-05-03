package com.burak.recipe.service;

import com.burak.recipe.entity.Recipe;
import com.burak.recipe.requestDto.recipe.RecipeSaveRequestDto;
import com.burak.recipe.responseDto.recipe.RecipeDeleteResponseDto;
import com.burak.recipe.responseDto.recipe.RecipeGetResponseDto;

import java.util.List;

public interface RecipeService {
    Recipe save(RecipeSaveRequestDto recipeSaveRequestDto);

    Recipe findById(long recipeId);

    RecipeGetResponseDto findRecipeGetResponseDtoById(long recipeId);

    RecipeGetResponseDto findRandomRecipe();

    List<RecipeGetResponseDto> findAll();

    List<RecipeGetResponseDto> findRecipesByCategory(String foodCategory);

    List<RecipeGetResponseDto> findRecipesByMemberId(long memberId);

    RecipeDeleteResponseDto deleteById(long recipeId);
}
