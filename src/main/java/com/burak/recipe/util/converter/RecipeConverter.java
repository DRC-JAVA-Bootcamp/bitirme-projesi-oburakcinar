package com.burak.recipe.util.converter;

import com.burak.recipe.entity.Recipe;
import com.burak.recipe.responseDto.recipe.RecipeGetResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeConverter {

    @Autowired
    private ModelMapper modelMapper;

    public List<RecipeGetResponseDto> convertFromRecipeListToGetResponseDtoList(List<Recipe> recipeList) {
        List<RecipeGetResponseDto> responseList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            responseList.add(modelMapper.map(recipe, RecipeGetResponseDto.class));
        }
        return responseList;
    }
}
