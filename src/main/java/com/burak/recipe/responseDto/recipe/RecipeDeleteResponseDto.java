package com.burak.recipe.responseDto.recipe;

import com.burak.recipe.entity.FoodCategoryEnum;
import lombok.Data;

@Data
public class RecipeDeleteResponseDto {

    private Long id;
    private FoodCategoryEnum foodCategory;
    private String name;
}
