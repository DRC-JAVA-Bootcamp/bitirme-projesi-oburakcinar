package com.burak.recipe.responseDto.recipe;

import com.burak.recipe.entity.FoodTypeEnum;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecipeGetResponseDto {

    private Long id;

    private long memberId;

    private FoodTypeEnum foodType;

    private String name;

    private LocalDateTime postDate;

    private int prepDuration;

    private List<String> ingredients;

    private List<String> prepSteps;

    private double cost;

    private double rating;


}
