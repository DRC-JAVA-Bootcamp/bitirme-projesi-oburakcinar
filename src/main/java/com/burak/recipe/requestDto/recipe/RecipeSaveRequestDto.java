package com.burak.recipe.requestDto.recipe;

import com.burak.recipe.validation.FoodCategory;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class RecipeSaveRequestDto {

    @NotEmpty(message = "name field cannot be empty")
    private String name;

    @FoodCategory //custom validation
    private String foodCategory;

    @Positive(message = "duration must be a positive number")
    private int prepDuration;

    @NotEmpty(message = "ingredients field cannot be empty")
    private List<String> ingredients;

    @NotEmpty(message = "prepSteps field cannot be empty")
    private List<String> prepSteps;

    @Positive(message = "cost must be a positive number")
    private double cost;


}
