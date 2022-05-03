package com.burak.recipe.responseDto.recipe;

import com.burak.recipe.entity.FoodCategoryEnum;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecipeGetResponseDto {

    private Long id;

    private long memberId;

    private FoodCategoryEnum foodCategory;

    private String name;

    private LocalDateTime postDate;

    private int prepDuration;

    private List<String> ingredients;

    private List<String> prepSteps;

    private double cost;

    private double rating;


}
