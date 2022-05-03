package com.burak.recipe.service;

import com.burak.recipe.entity.Recipe;
import com.burak.recipe.requestDto.recipe.RecipeSaveRequestDto;
import com.burak.recipe.responseDto.recipe.RecipeGetResponseDto;

public interface RecipeService {
    Recipe save(RecipeSaveRequestDto recipeSaveRequestDto);

    RecipeGetResponseDto findById(long recipeId);
}
