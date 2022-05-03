package com.burak.recipe.service;

import com.burak.recipe.entity.Recipe;
import com.burak.recipe.requestDto.recipe.RecipeSaveRequestDto;

public interface RecipeService {
    Recipe save(RecipeSaveRequestDto recipeSaveRequestDto);
}
